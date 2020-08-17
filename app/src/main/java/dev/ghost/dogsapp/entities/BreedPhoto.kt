package dev.ghost.dogsapp.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class BreedPhoto(
    @PrimaryKey
    override val path:String = "",
    @ForeignKey(entity = Breed::class, childColumns = ["breedName"], parentColumns = ["name"])
    val breedName:String,
    override var liked:Boolean = false
) : ItemImage,Parcelable{
}