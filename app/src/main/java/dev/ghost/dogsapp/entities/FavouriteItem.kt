package dev.ghost.dogsapp.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavouriteItem(
    val name:String,
    val photos:List<ItemImage>
):Parcelable {
}