package dev.ghost.dogsapp.entities

import android.os.Parcelable
import dev.ghost.dogsapp.R
import kotlinx.android.parcel.Parcelize

interface ItemImage : Parcelable {
    val path: String
    var liked: Boolean
}