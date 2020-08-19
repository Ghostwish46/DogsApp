package dev.ghost.dogsapp.model.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import dev.ghost.dogsapp.model.dao.BreedDao
import dev.ghost.dogsapp.model.entities.Breed
import dev.ghost.dogsapp.model.domain.BreedWithPhotos
import dev.ghost.dogsapp.model.network.ApiService

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class BreedRepository(
    private val apiService: ApiService,
    private val breedDao: BreedDao
) {
    lateinit var data: LiveData<List<BreedWithPhotos>>

    fun getBreedData(parentBreed: Breed?) {
        data = if (parentBreed == null)
            breedDao.getAllBreeds()
        else
            breedDao.getAllSubBreedsByBreed(parentBreed.name)
    }

    suspend fun getSubBreedsCount(parentBreed: Breed?) =
        breedDao.getSubBreedsCountByBreed(parentBreed?.name ?: "")

    suspend fun refresh() {
        withContext(Dispatchers.IO)
        {
            try {
                val breedWithSubBreedsResponse = apiService.getBreedsAsync()
                    .await()

                val tempObject = JSONObject(breedWithSubBreedsResponse.toString())
                val tempMessage = tempObject.getJSONObject("message")
                tempMessage.keys().forEach {
                    val tempBreed =
                        Breed(it.capitalize(), "")
                    breedDao.add(tempBreed)

                    val jsonSubBreeds = tempMessage.getJSONArray(it)
                    Array(jsonSubBreeds.length()) { subKey ->
                        val tempSubBreed =
                            Breed(
                                jsonSubBreeds.getString(subKey).capitalize(), tempBreed.name.capitalize()
                            )
                        breedDao.add(tempSubBreed)
                    }
                }

            } catch (ex: Exception) {
                Log.e("ERROR", ex.message.toString())
            }
        }
    }
}