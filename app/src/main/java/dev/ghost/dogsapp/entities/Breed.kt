package dev.ghost.dogsapp.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "breeds")
data class Breed(
    @PrimaryKey
    val name: String = ""
) : Parcelable {

}