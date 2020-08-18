package dev.ghost.dogsapp.viewmodel.breeds

import android.app.Application
import androidx.lifecycle.*
import dev.ghost.dogsapp.model.entities.Breed
import dev.ghost.dogsapp.model.domain.BreedWithPhotos
import dev.ghost.dogsapp.model.network.ApiUtils
import dev.ghost.dogsapp.model.db.AppDatabase
import dev.ghost.dogsapp.model.network.LoadingState
import dev.ghost.dogsapp.model.repositories.BreedRepository
import dev.ghost.dogsapp.viewmodel.breeds.BreedsAdapter
import kotlinx.coroutines.launch

class BreedsViewModel(application: Application) : AndroidViewModel(application) {

    private val _loadingState = MutableLiveData<LoadingState>()

    private val appDatabase = AppDatabase.getDatabase(application)
    private val apiService = ApiUtils.apiService

    private val breedRepository =
        BreedRepository(
            apiService,
            appDatabase.breedDao
        )
    lateinit var breedAdapter: BreedsAdapter

    var parentBreed: Breed? = null

    lateinit var breedsFullInfoData: LiveData<List<BreedWithPhotos>>


    fun updateData() {

        breedRepository.getBreedData(parentBreed)
        breedsFullInfoData = breedRepository.data

        fetchItems()
    }

    suspend fun getSubBreedsCount(breed: Breed) =
        breedRepository.getSubBreedsCount(breed)

    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private fun fetchItems() {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                breedRepository.refresh()
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }
}