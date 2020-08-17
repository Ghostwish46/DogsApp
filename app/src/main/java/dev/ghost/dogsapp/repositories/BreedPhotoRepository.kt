package dev.ghost.dogsapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import dev.ghost.dogsapp.dao.BreedPhotoDao
import dev.ghost.dogsapp.entities.Breed
import dev.ghost.dogsapp.entities.BreedPhoto
import dev.ghost.dogsapp.entities.SubBreed
import dev.ghost.dogsapp.helpers.ApiService

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
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
            try {
                val breedPhotos = apiService.getBreedPhotosAsync(breed.name)
                    .await()

                val response =
                    breedPhotos.toString().substringAfter("[")
                        .substringBefore("]")
                        .replace(" ", "")

                val pathItems = response.split(",")
                pathItems.forEach {
                    val tempBreedPhoto = BreedPhoto(it, breed.name)
                    breedPhotoDao.add(tempBreedPhoto)
                }
            } catch (ex: Exception) {
                Log.e("ERROR", ex.message.toString())
            }
        }
    }
}