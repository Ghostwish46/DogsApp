package dev.ghost.dogsapp.ui.images

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dev.ghost.dogsapp.ui.MainActivity
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.model.domain.BreedWithPhotos
import dev.ghost.dogsapp.model.network.Status
import dev.ghost.dogsapp.ui.AlertDialogHelper
import dev.ghost.dogsapp.viewmodel.images.ImagesAdapter
import dev.ghost.dogsapp.viewmodel.images.ImagesViewModel
import kotlinx.android.synthetic.main.activity_images.*
import kotlinx.android.synthetic.main.layout_bottom_share.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagesActivity : AppCompatActivity() {

    private lateinit var imagesViewModel: ImagesViewModel

    private lateinit var bottomBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
        setSupportActionBar(findViewById(R.id.toolbar))

        val currentItem = intent.extras?.get(MainActivity.EXTRA_ITEM)
        val isFavourite =
            intent.extras?.getBoolean(MainActivity.FLAG_FAVOURITE, false) ?: false

        if (currentItem is BreedWithPhotos) {
            titleSubBreed.text = currentItem.breed.name

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
                        progressBarImages.visibility = if (it.isNotEmpty()) {
                            imagesViewModel.imagesAdapter.updateImages(it)
                            View.GONE
                        } else
                            View.VISIBLE
                    })
                imagesViewModel.loadingState.observe(this, Observer {
                    if (it.status == Status.FAILED) {
                        progressBarImages.visibility = View.GONE
                        AlertDialogHelper().showConnectionErrorDialog(this)
                    }
                })
            }
        }

        bottomBehavior = BottomSheetBehavior.from(bottom_sheet_share)

        textViewBottomActionShare.setOnClickListener {
            Toast.makeText(this, getString(R.string.message_photo_sent), Toast.LENGTH_LONG).show()
            bottomBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        textViewBottomActionCancel.setOnClickListener {
            bottomBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun goBack(view: View) {
        onBackPressed()
    }

    fun shareImage(view: View) {
        bottomBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}