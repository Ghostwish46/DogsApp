package dev.ghost.dogsapp.ui.breeds

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ghost.dogsapp.MainActivity
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.entities.BreedWithSubBreeds
import dev.ghost.dogsapp.ui.images.ImagesActivity
import dev.ghost.dogsapp.ui.subBreeds.SubBreedActivity
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment(), BreedsAdapter.OnItemClickListener {

    companion object{
        const val BREED = "Breed"
    }

    private lateinit var listViewModel: ListViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        listViewModel =
            ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.breedAdapter =
            BreedsAdapter(this)

        val root = inflater.inflate(R.layout.fragment_list, container, false)

        root.recyclerViewListFragment.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listViewModel.breedAdapter
        }

        listViewModel.breedsFullInfoData.observe(viewLifecycleOwner, Observer {
            listViewModel.breedAdapter.updateBreeds(it)
        })

        return root
    }

    override fun onClick(fullBreed: BreedWithSubBreeds) {
        if (fullBreed.subBreeds.isEmpty())
        {
            val intentImages = Intent(context, ImagesActivity::class.java)
            intentImages.putExtra(MainActivity.EXTRA_ITEM, fullBreed.breed)
            context?.startActivity(intentImages)
        }
        else
        {
            val intentSubBreed = Intent(context, SubBreedActivity::class.java)
            intentSubBreed.putExtra(BREED, fullBreed)
            context?.startActivity(intentSubBreed)
        }
    }
}