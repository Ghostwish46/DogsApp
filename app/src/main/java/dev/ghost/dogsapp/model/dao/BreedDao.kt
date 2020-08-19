package dev.ghost.dogsapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ghost.dogsapp.model.entities.Breed
import dev.ghost.dogsapp.model.domain.BreedWithPhotos

@Dao
interface BreedDao {
    @Query("Select * from breeds where parentName = ''")
    @Transaction
    fun getAllBreeds(): LiveData<List<BreedWithPhotos>>

    @Query("Select Count(*) from breeds where parentName = :breedName")
    fun getSubBreedsCountByBreed(breedName: String): Int

    @Query("Select * from breeds where parentName = :breedName")
    @Transaction
    fun getAllSubBreedsByBreed(breedName: String): LiveData<List<BreedWithPhotos>>

    @Query("Select * FROM breeds INNER JOIN breedphoto ON breeds.name = breedphoto.breedName WHERE (breedphoto.liked = 1)")
    @Transaction
    fun getFavouriteBreeds(): LiveData<List<BreedWithPhotos>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(breeds: List<Breed>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(breed: Breed)
}