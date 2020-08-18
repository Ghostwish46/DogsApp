package dev.ghost.dogsapp.viewmodel.favourites

import android.app.Application
import androidx.lifecycle.*
import dev.ghost.dogsapp.model.db.AppDatabase

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {

    private var appDatabase: AppDatabase = AppDatabase.getDatabase(application)
    var favouriteBreeds = appDatabase.breedDao.getFavouriteBreeds()
}