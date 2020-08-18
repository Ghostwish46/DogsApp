package dev.ghost.dogsapp.ui.subBreeds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ghost.dogsapp.ui.MainActivity
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.model.entities.Breed
import dev.ghost.dogsapp.ui.images.ImagesActivity
import dev.ghost.dogsapp.ui.breeds.ListFragment
import dev.ghost.dogsapp.viewmodel.breeds.BreedsAdapter
import dev.ghost.dogsapp.viewmodel.breeds.BreedsViewModel
import kotlinx.android.synthetic.main.activity_sub_breed.*

class SubBreedActivity : AppCompatActivity() {

    private lateinit var subBreedViewModel: BreedsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_breed)

        subBreedViewModel = ViewModelProvider(this).get(BreedsViewModel::class.java)

        val currentItem = intent.extras?.get(ListFragment.BREED)
        if (currentItem is Breed)
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
            subBreedViewModel.breedAdapter.updateBreeds(it)
        })
    }
}