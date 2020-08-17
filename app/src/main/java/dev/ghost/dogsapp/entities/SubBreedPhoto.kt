package dev.ghost.dogsapp.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class SubBreedPhoto(
    @PrimaryKey
    override val path: String = "",
    val subBreedName: String = "",
    override
    var liked: Boolean = false
) : ItemImage