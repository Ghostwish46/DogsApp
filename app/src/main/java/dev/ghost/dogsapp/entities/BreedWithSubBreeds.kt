package dev.ghost.dogsapp.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
class BreedWithSubBreeds(
    @Embedded
    val breed: Breed,
    @Relation(parentColumn = "name", entityColumn = "breedName", entity = SubBreed::class)
    val subBreeds: List<SubBreed>,
    @Relation(parentColumn = "name", entityColumn = "breedName", entity = BreedPhoto::class)
    var breedPhotos: List<BreedPhoto>
) : Parcelable {

}