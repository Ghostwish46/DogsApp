package dev.ghost.dogsapp.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

class SubBreedWithPhotos(
    @Embedded
    val subBreed: SubBreed,
    @Relation(
        parentColumn = "subBreedName",
        entityColumn = "subBreedName",
        entity = SubBreedPhoto::class
    )
    var subBreedPhotos: List<SubBreedPhoto>
) {
}