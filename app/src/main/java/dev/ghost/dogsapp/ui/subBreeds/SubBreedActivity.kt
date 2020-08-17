package dev.ghost.dogsapp.ui.subBreeds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ghost.dogsapp.MainActivity
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.entities.BreedWithSubBreeds
import dev.ghost.dogsapp.entities.SubBreedWithPhotos
import dev.ghost.dogsapp.ui.images.ImagesActivity
import dev.ghost.dogsapp.ui.breeds.ListFragment
import kotlinx.android.synthetic.main.activity_sub_breed.*

class SubBreedActivity : AppCompatActivity(), SubBreedsAdapter.OnItemClickListener {

    private lateinit var subBreedViewModel: SubBreedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_breed)

        subBreedViewModel = ViewModelProvider(this).get(SubBreedViewModel::class.java)

        val breedWithSubBreeds = intent.extras?.get(ListFragment.BREED) as? BreedWithSubBreeds

        subBreedViewModel.subBreedAdapter = SubBreedsAdapter(this)

        recyclerViewSubBreeds.apply {
            layoutManager = LinearLayoutManager(this@SubBreedActivity)
            adapter = subBreedViewModel.subBreedAdapter
        }

        subBreedViewModel
            .getSubBreeds(breedWithSubBreeds?.breed?.name ?: "")

        subBreedViewModel.subBreedsWithPhoto.observe(this, Observer {
            subBreedViewModel.subBreedAdapter.updateSubBreeds(it)
        })
    }

    override fun onClick(fullSubBreed: SubBreedWithPhotos) {
        val intentImages = Intent(this@SubBreedActivity, ImagesActivity::class.java)
        intentImages.putExtra(MainActivity.EXTRA_ITEM, fullSubBreed.subBreed)
        startActivity(intentImages)
    }
}