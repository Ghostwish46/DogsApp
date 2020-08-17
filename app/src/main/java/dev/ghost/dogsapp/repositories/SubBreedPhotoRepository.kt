package dev.ghost.dogsapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import dev.ghost.dogsapp.dao.SubBreedPhotoDao
import dev.ghost.dogsapp.entities.BreedPhoto
import dev.ghost.dogsapp.entities.SubBreed
import dev.ghost.dogsapp.entities.SubBreedPhoto
import dev.ghost.dogsapp.helpers.ApiService

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class SubBreedPhotoRepository(
    private val apiService: ApiService,
    private val subBreedPhotoDao: SubBreedPhotoDao,
    private val subBreed: SubBreed
) {
    var data: LiveData<List<SubBreedPhoto>> =
        subBreedPhotoDao.getAllBySubBreedName(subBreed.subBreedName)

    suspend fun refresh() {
        withContext(Dispatchers.IO)
        {
            try {
                val subBreedPhotos =
                    apiService.getSubBreedPhotosAsync(subBreed.breedName, subBreed.subBreedName)
                        .await()

                val response = subBreedPhotos.toString().substringAfter("[")
                    .substringBefore("]")
                    .replace(" ", "")

                val pathItems = response.split(",")
                pathItems.forEach {
                    val tempBreedPhoto =
                        SubBreedPhoto(it, subBreed.subBreedName)
                    subBreedPhotoDao.add(tempBreedPhoto)
                }
            } catch (ex: Exception) {
                Log.e("ERROR", ex.message.toString())
            }
        }
    }
}