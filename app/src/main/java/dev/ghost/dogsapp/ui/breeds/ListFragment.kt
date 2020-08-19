package dev.ghost.dogsapp.ui.breeds

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ghost.dogsapp.ui.MainActivity
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.model.network.LoadingState
import dev.ghost.dogsapp.model.network.Status
import dev.ghost.dogsapp.ui.AlertDialogHelper
import dev.ghost.dogsapp.ui.images.ImagesActivity
import dev.ghost.dogsapp.ui.subBreeds.SubBreedActivity
import dev.ghost.dogsapp.viewmodel.breeds.BreedsAdapter
import dev.ghost.dogsapp.viewmodel.breeds.BreedsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alert_server_error.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListFragment : Fragment() {

    companion object {
        const val BREED = "Breed"
    }

    private lateinit var listViewModel: BreedsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).titleMain.text = getString(R.string.title_list)

        listViewModel =
            ViewModelProvider(this).get(BreedsViewModel::class.java)
        listViewModel.updateData()

        listViewModel.breedAdapter =
            BreedsAdapter()
            {

                listViewModel.viewModelScope.launch(Dispatchers.IO) {
                    val subBreedsCount = listViewModel.getSubBreedsCount(it.breed)
                    withContext(Dispatchers.Main)
                    {
                        if (subBreedsCount > 0) {
                            val intentSubBreed = Intent(context, SubBreedActivity::class.java)
                            intentSubBreed.putExtra(BREED, it.breed)
                            context?.startActivity(intentSubBreed)
                        } else {
                            val intentImages = Intent(context, ImagesActivity::class.java)
                            intentImages.putExtra(MainActivity.EXTRA_ITEM, it)
                            context?.startActivity(intentImages)
                        }
                    }
                }
            }

        val root = inflater.inflate(R.layout.fragment_list, container, false)

        root.recyclerViewListFragment.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listViewModel.breedAdapter
        }

        listViewModel.breedsFullInfoData.observe(viewLifecycleOwner, Observer {
            root.progressBarList.visibility = if (it.isNotEmpty()) {
                listViewModel.breedAdapter.updateBreeds(it)
                View.GONE
            } else
                View.VISIBLE
        })

        listViewModel.loadingState.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.FAILED) {
                context?.let {
                    AlertDialogHelper().showConnectionErrorDialog(it)
                }
                root.progressBarList.visibility = View.GONE
            }
        })

        return root
    }
}