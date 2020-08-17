package dev.ghost.dogsapp.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "subBreeds")
class SubBreed(
    @PrimaryKey
    val subBreedName: String = "",
    val breedName: String = ""
) : Parcelable