package dev.ghost.dogsapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ghost.dogsapp.entities.Breed
import dev.ghost.dogsapp.entities.BreedPhoto
import dev.ghost.dogsapp.entities.BreedWithSubBreeds
import dev.ghost.dogsapp.entities.SubBreedPhoto

@Dao
interface SubBreedPhotoDao {
    @Query("Select * from subbreedphoto where subBreedName = :subBreedName")
    @Transaction
    fun getAllBySubBreedName(subBreedName:String): LiveData<List<SubBreedPhoto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(breeds:List<SubBreedPhoto>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(breed:SubBreedPhoto)

    @Update
    fun update(breed: SubBreedPhoto)
}