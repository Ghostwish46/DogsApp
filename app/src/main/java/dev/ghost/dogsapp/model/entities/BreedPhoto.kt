package dev.ghost.dogsapp.model.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.ghost.dogsapp.model.entities.Breed
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class BreedPhoto(
    @PrimaryKey
    val path: String = "",
    @ForeignKey(entity = Breed::class, childColumns = ["breedName"], parentColumns = ["name"])
    val breedName: String,
    var liked: Boolean = false
) : Parcelable {
}