package dev.ghost.dogsapp.ui.images

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.ghost.dogsapp.MainActivity
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.entities.Breed
import dev.ghost.dogsapp.entities.FavouriteItem
import dev.ghost.dogsapp.entities.SubBreed
import kotlinx.android.synthetic.main.activity_images.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagesActivity : AppCompatActivity() {

    private lateinit var imagesViewModel: ImagesViewModel
    private lateinit var imagesAdapter:ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
        setSupportActionBar(findViewById(R.id.toolbar))

        val currentItem = intent.extras?.get(MainActivity.EXTRA_ITEM)
        currentItem?.let {
            imagesViewModel = ViewModelProvider(this@ImagesActivity).get(ImagesViewModel::class.java)
            imagesViewModel.item = currentItem
            imagesViewModel.initializeData()

            imagesAdapter = ImagesAdapter{
                it.liked = !it.liked
                imagesViewModel.viewModelScope.launch(Dispatchers.IO){
                    imagesViewModel.updateItem(it)
                }
                imagesAdapter.notifyDataSetChanged()
            }

            viewPagerImages.adapter = imagesAdapter

            if (currentItem is Breed)
            {
                imagesViewModel.breedImagesData.observe(this@ImagesActivity, Observer {
                    imagesAdapter.updateImages(it)
                })
            }
            if (currentItem is SubBreed)
            {
                imagesViewModel.subBreedImagesData.observe(this@ImagesActivity, Observer {
                    imagesAdapter.updateImages(it)
                })
            }
            if (currentItem is FavouriteItem)
            {
                imagesAdapter.updateImages(currentItem.photos)
            }
        }
    }
}