package dev.ghost.dogsapp.ui.subBreeds

import android.app.Application
import androidx.lifecycle.*
import dev.ghost.dogsapp.entities.SubBreedWithPhotos
import dev.ghost.dogsapp.helpers.AppDatabase

class SubBreedViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var subBreedsWithPhoto: LiveData<List<SubBreedWithPhotos>>
    lateinit var subBreedAdapter: SubBreedsAdapter

    private val appDatabase = AppDatabase.getDatabase(application)

    fun getSubBreeds(currentBreedName: String) {
        subBreedsWithPhoto = appDatabase.subBreedDao.getAllByBreed(currentBreedName)
    }
}