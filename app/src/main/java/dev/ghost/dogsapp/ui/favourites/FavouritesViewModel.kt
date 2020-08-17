package dev.ghost.dogsapp.ui.favourites

import android.app.Application
import androidx.lifecycle.*
import dev.ghost.dogsapp.entities.BreedWithSubBreeds
import dev.ghost.dogsapp.entities.FavouriteItem
import dev.ghost.dogsapp.entities.SubBreedWithPhotos
import dev.ghost.dogsapp.helpers.AppDatabase

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {

    private var appDatabase: AppDatabase = AppDatabase.getDatabase(application)
    var favouriteItems = getFavouritesData()

    private fun getFavouritesData(): LiveData<List<FavouriteItem>> {
        val breedData = appDatabase.breedDao.getAll()
        val subBreedData = appDatabase.subBreedDao.getAll()


        val favouriteLiveData: MediatorLiveData<List<FavouriteItem>> = MediatorLiveData()
        favouriteLiveData.addSource(breedData) {
            favouriteLiveData.value = combineLatestData(breedData, subBreedData)
        }
        favouriteLiveData.addSource(subBreedData) {
            favouriteLiveData.value = combineLatestData(breedData, subBreedData)
        }
        return favouriteLiveData
    }

    private fun combineLatestData(
        breedResult: LiveData<List<BreedWithSubBreeds>>,
        subBreedResult: LiveData<List<SubBreedWithPhotos>>
    ): List<FavouriteItem> {
        val favList = mutableListOf<FavouriteItem>()

        val firstResult = breedResult.value
        if (firstResult != null) {
            firstResult.forEach {
                it.breedPhotos = it.breedPhotos.filter {
                    it.liked
                }
            }
            firstResult.filter {
                it.breedPhotos.isNotEmpty()
            }.forEach {
                favList.add(FavouriteItem(it.breed.name, it.breedPhotos))
            }
        }

        val secondResult = subBreedResult.value
        if (secondResult != null) {
            secondResult.forEach {
                it.subBreedPhotos = it.subBreedPhotos.filter {
                    it.liked
                }
            }
            secondResult.filter {
                it.subBreedPhotos.isNotEmpty()
            }
                .forEach {
                    favList.add(FavouriteItem(it.subBreed.subBreedName, it.subBreedPhotos))
                }
        }

        return favList
    }
}