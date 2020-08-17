package dev.ghost.dogsapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dev.ghost.dogsapp.dao.BreedDao
import dev.ghost.dogsapp.dao.SubBreedDao
import dev.ghost.dogsapp.entities.Breed
import dev.ghost.dogsapp.entities.BreedWithSubBreeds
import dev.ghost.dogsapp.entities.SubBreed
import dev.ghost.dogsapp.helpers.ApiService

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class BreedRepository(
    private val apiService: ApiService,
    private val breedDao: BreedDao,
    private val subBreedDao: SubBreedDao
) {
    var data: LiveData<List<BreedWithSubBreeds>> = breedDao.getAll()

    suspend fun refresh() {
        withContext(Dispatchers.IO)
        {
            try {
                val breedWithSubBreedsResponse = apiService.getBreedsAsync()
                    .await()

                val tempObject = JSONObject(breedWithSubBreedsResponse.toString())
                val tempMessage = tempObject.getJSONObject("message")
                tempMessage.keys().forEach {
                    val tempBreed = Breed(it)
                    breedDao.add(tempBreed)

                    val jsonSubBreeds = tempMessage.getJSONArray(it)
                    Array(jsonSubBreeds.length()) {subKey ->
                        val tempSubBreed = SubBreed(jsonSubBreeds.getString(subKey), tempBreed.name)
                        subBreedDao.add(tempSubBreed)
                    }
                }

            } catch (ex: Exception) {
                Log.e("ERROR", ex.message.toString())
            }
        }
    }
}