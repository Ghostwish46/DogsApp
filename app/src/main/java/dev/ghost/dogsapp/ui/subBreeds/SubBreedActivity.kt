package dev.ghost.dogsapp.ui.subBreeds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ghost.dogsapp.ui.MainActivity
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.model.entities.Breed
import dev.ghost.dogsapp.model.network.Status
import dev.ghost.dogsapp.ui.AlertDialogHelper
import dev.ghost.dogsapp.ui.images.ImagesActivity
import dev.ghost.dogsapp.ui.breeds.ListFragment
import dev.ghost.dogsapp.viewmodel.breeds.BreedsAdapter
import dev.ghost.dogsapp.viewmodel.breeds.BreedsViewModel
import kotlinx.android.synthetic.main.activity_sub_breed.*
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.android.synthetic.main.fragment_list.view.*

class SubBreedActivity : AppCompatActivity() {

    private lateinit var subBreedViewModel: BreedsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_breed)
        setSupportActionBar(toolbarSubBreeds)

        subBreedViewModel = ViewModelProvider(this).get(BreedsViewModel::class.java)

        val currentItem = intent.extras?.get(ListFragment.BREED)
        if (currentItem is Breed) {
            titleSubBreed.text = currentItem.name
            subBreedViewModel.parentBreed = currentItem
            subBreedViewModel.updateData()

            subBreedViewModel.breedAdapter =
                BreedsAdapter {
                    val intentImages = Intent(this@SubBreedActivity, ImagesActivity::class.java)
                    intentImages.putExtra(MainActivity.EXTRA_ITEM, it)
                    startActivity(intentImages)
                }

            recyclerViewSubBreeds.apply {
                layoutManager = LinearLayoutManager(this@SubBreedActivity)
                adapter = subBreedViewModel.breedAdapter
            }

            subBreedViewModel.breedsFullInfoData.observe(this, Observer {
                progressBarSubBreeds.visibility = if (it.isNotEmpty()) {
                    subBreedViewModel.breedAdapter.updateBreeds(it)
                    View.GONE
                } else
                    View.VISIBLE
            })

            subBreedViewModel.loadingState.observe(this, Observer {
                when (it.status) {
                    Status.FAILED -> {
                        AlertDialogHelper().showConnectionErrorDialog(this)
                        progressBarSubBreeds.visibility = View.GONE
                    }
                    else -> {
                    }
                }
            })
        }
    }

    fun goBack(view: View) {
        onBackPressed()
    }
}