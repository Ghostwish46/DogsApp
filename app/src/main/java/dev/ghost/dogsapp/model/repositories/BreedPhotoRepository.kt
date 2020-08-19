package dev.ghost.dogsapp.model.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import dev.ghost.dogsapp.model.dao.BreedPhotoDao
import dev.ghost.dogsapp.model.entities.Breed
import dev.ghost.dogsapp.model.entities.BreedPhoto
import dev.ghost.dogsapp.model.network.ApiService

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class BreedPhotoRepository(
    private val apiService: ApiService,
    private val breedPhotoDao: BreedPhotoDao,
    private val breed: Breed
) {
    var data: LiveData<List<BreedPhoto>> = breedPhotoDao.getAllByBreedName(breed.name)

    suspend fun refresh() {
        withContext(Dispatchers.IO)
        {
            val breedPhotos = if (breed.parentName.isEmpty()) {
                apiService.getBreedPhotosAsync(breed.name.toLowerCase())
                    .await()
            } else {
                apiService.getSubBreedPhotosAsync(
                    breed.parentName.toLowerCase(),
                    breed.name.toLowerCase()
                )
                    .await()
            }

            val response =
                breedPhotos.toString().substringAfter("[")
                    .substringBefore("]")
                    .replace(" ", "")
            val pathItems = response.split(",")

            pathItems.forEach {
                val tempBreedPhoto =
                    BreedPhoto(
                        it,
                        breed.name
                    )
                breedPhotoDao.add(tempBreedPhoto)
            }
        }
    }
}