package dev.ghost.dogsapp.ui.images

import android.app.Application
import androidx.lifecycle.*
import dev.ghost.dogsapp.entities.*
import dev.ghost.dogsapp.helpers.ApiUtils
import dev.ghost.dogsapp.helpers.AppDatabase
import dev.ghost.dogsapp.helpers.LoadingState
import dev.ghost.dogsapp.repositories.BreedPhotoRepository
import dev.ghost.dogsapp.repositories.BreedRepository
import dev.ghost.dogsapp.repositories.SubBreedPhotoRepository
import dev.ghost.dogsapp.ui.breeds.BreedsAdapter
import kotlinx.coroutines.launch
import java.time.temporal.UnsupportedTemporalTypeException

class ImagesViewModel(application: Application) : AndroidViewModel(application) {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    lateinit var breedImagesData: LiveData<List<BreedPhoto>>
    lateinit var subBreedImagesData: LiveData<List<SubBreedPhoto>>
    lateinit var favouriteImagesData: LiveData<List<SubBreedPhoto>>

    lateinit var breedPhotoRepository: BreedPhotoRepository
    lateinit var subBreedPhotoRepository: SubBreedPhotoRepository

    lateinit var item: Any
    val appDatabase = AppDatabase.getDatabase(application)
    val apiService = ApiUtils.apiService


    fun initializeData() {
        if (item is Breed) {
            breedPhotoRepository = BreedPhotoRepository(
                apiService, appDatabase.breedPhotoDao,
                item as Breed
            )
            breedImagesData = breedPhotoRepository.data
            fetchBreedPhotos()
        }
        if (item is SubBreed) {
            subBreedPhotoRepository =
                SubBreedPhotoRepository(apiService, appDatabase.subBreedPhotoDao, item as SubBreed)
            subBreedImagesData = subBreedPhotoRepository.data
            fetchSubBreedPhotos()
        }
    }

    private fun fetchBreedPhotos() {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                breedPhotoRepository.refresh()
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }

    private fun fetchSubBreedPhotos() {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                subBreedPhotoRepository.refresh()
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }

    suspend fun updateItem(itemImage: ItemImage) {
        if (itemImage is BreedPhoto)
            appDatabase.breedPhotoDao.update(itemImage)
        if (itemImage is SubBreedPhoto)
            appDatabase.subBreedPhotoDao.update(itemImage)
    }
}