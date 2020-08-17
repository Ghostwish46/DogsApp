package dev.ghost.dogsapp.ui.breeds

import android.app.Application
import androidx.lifecycle.*
import dev.ghost.dogsapp.entities.BreedWithSubBreeds
import dev.ghost.dogsapp.helpers.ApiUtils
import dev.ghost.dogsapp.helpers.AppDatabase
import dev.ghost.dogsapp.helpers.LoadingState
import dev.ghost.dogsapp.repositories.BreedRepository
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val _loadingState = MutableLiveData<LoadingState>()

    private val breedRepository: BreedRepository
    lateinit var breedAdapter: BreedsAdapter

    val breedsFullInfoData: LiveData<List<BreedWithSubBreeds>>

    init {
        val appDatabase = AppDatabase.getDatabase(application)
        val apiService = ApiUtils.apiService

        breedRepository = BreedRepository(apiService, appDatabase.breedDao, appDatabase.subBreedDao)

        breedsFullInfoData = breedRepository.data
        fetchItems()
    }

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