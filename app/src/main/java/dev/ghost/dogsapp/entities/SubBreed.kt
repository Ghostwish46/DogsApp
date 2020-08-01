package dev.ghost.dogsapp.entities

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "subBreeds")
class SubBreed(
    val name:String = "",
    val breedId:Int = 0,
    @Embedded
    val photos: List<Photo> = listOf()
) {
}