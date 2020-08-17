package dev.ghost.dogsapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ghost.dogsapp.entities.Breed
import dev.ghost.dogsapp.entities.BreedPhoto
import dev.ghost.dogsapp.entities.BreedWithSubBreeds

@Dao
interface BreedPhotoDao {
    @Query("Select * from breedphoto where breedName = :breedName")
    @Transaction
    fun getAllByBreedName(breedName:String): LiveData<List<BreedPhoto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(breeds:List<BreedPhoto>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(breed:BreedPhoto)

    @Update
    fun update(breed: BreedPhoto)
}