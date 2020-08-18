package dev.ghost.dogsapp.ui.images

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.ghost.dogsapp.ui.MainActivity
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.model.domain.BreedWithPhotos
import dev.ghost.dogsapp.viewmodel.images.ImagesAdapter
import dev.ghost.dogsapp.viewmodel.images.ImagesViewModel
import kotlinx.android.synthetic.main.activity_images.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagesActivity : AppCompatActivity() {

    private lateinit var imagesViewModel: ImagesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
        setSupportActionBar(findViewById(R.id.toolbar))

        val currentItem = intent.extras?.get(MainActivity.EXTRA_ITEM)
        val isFavourite =
            intent.extras?.getBoolean(MainActivity.FLAG_FAVOURITE, false) ?: false

        if (currentItem is BreedWithPhotos) {
            imagesViewModel = ViewModelProvider(this@ImagesActivity)
                .get(ImagesViewModel::class.java)

            imagesViewModel.imagesAdapter =
                ImagesAdapter {
                    it.liked = !it.liked
                    imagesViewModel.viewModelScope.launch(Dispatchers.IO) {
                        imagesViewModel.updateBreed(it)
                    }
                    imagesViewModel.imagesAdapter.notifyDataSetChanged()
                }

            viewPagerImages.adapter = imagesViewModel.imagesAdapter

            if (isFavourite) {
                imagesViewModel.imagesAdapter.updateImages(currentItem.breedPhotos)
            } else {
                imagesViewModel.breed = currentItem.breed
                imagesViewModel.initializeData()
                imagesViewModel.breedImagesData.observe(this@ImagesActivity,
                    Observer {
                        imagesViewModel.imagesAdapter.updateImages(it)
                    })
            }
        }
    }
}