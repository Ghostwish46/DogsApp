package dev.ghost.dogsapp.viewmodel.images

import android.app.Application
import androidx.lifecycle.*
import dev.ghost.dogsapp.model.network.ApiUtils
import dev.ghost.dogsapp.model.db.AppDatabase
import dev.ghost.dogsapp.model.network.LoadingState
import dev.ghost.dogsapp.model.entities.Breed
import dev.ghost.dogsapp.model.entities.BreedPhoto
import dev.ghost.dogsapp.model.repositories.BreedPhotoRepository
import kotlinx.coroutines.launch

class ImagesViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var breedImagesData: LiveData<List<BreedPhoto>>
    lateinit var breed: Breed
    lateinit var imagesAdapter: ImagesAdapter

    private val appDatabase = AppDatabase.getDatabase(application)
    private val apiService = ApiUtils.apiService
    private lateinit var breedPhotoRepository: BreedPhotoRepository

    val loadingState = MutableLiveData<LoadingState>()

    fun initializeData() {
        breedPhotoRepository =
            BreedPhotoRepository(
                apiService, appDatabase.breedPhotoDao,
                breed
            )
        breedImagesData = breedPhotoRepository.data
        fetchBreedPhotos()
    }

    private fun fetchBreedPhotos() {
        viewModelScope.launch {
            try {
                loadingState.value = LoadingState.LOADING
                breedPhotoRepository.refresh()
                loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                loadingState.value = LoadingState.error(e.message)
            }
        }
    }

    suspend fun updateBreed(breedPhoto: BreedPhoto) {
        appDatabase.breedPhotoDao.update(breedPhoto)
    }
}