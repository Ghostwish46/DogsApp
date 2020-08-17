package dev.ghost.dogsapp.response

import com.google.gson.annotations.SerializedName
import dev.ghost.dogsapp.entities.BreedWithSubBreeds

class ResponseBreeds(
    @SerializedName("message")
    val breedWithSubBreeds: BreedWithSubBreeds,
    val status:String
) {
}