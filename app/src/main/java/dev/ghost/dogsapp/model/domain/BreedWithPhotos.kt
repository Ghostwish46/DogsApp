package dev.ghost.dogsapp.model.domain

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import dev.ghost.dogsapp.model.entities.Breed
import dev.ghost.dogsapp.model.entities.BreedPhoto
import kotlinx.android.parcel.Parcelize

@Parcelize
class BreedWithPhotos(
    @Embedded
    val breed: Breed,
    @Relation(
        parentColumn = "name",
        entityColumn = "breedName",
        entity = BreedPhoto::class
    )
    var breedPhotos: List<BreedPhoto>
) : Parcelable {

}