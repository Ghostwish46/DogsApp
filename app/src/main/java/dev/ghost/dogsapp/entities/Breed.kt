package dev.ghost.dogsapp.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class Breed(
    @PrimaryKey
    val name: String = "",
    @Embedded
    val photos: List<Photo> = listOf()
    ) {

}