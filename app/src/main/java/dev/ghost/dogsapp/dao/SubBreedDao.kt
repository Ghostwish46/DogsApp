package dev.ghost.dogsapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ghost.dogsapp.entities.BreedWithSubBreeds
import dev.ghost.dogsapp.entities.SubBreed
import dev.ghost.dogsapp.entities.SubBreedWithPhotos

@Dao
interface SubBreedDao {
    @Query("Select * from subBreeds where breedName = :breedName")
    @Transaction
    fun getAllByBreed(breedName: String): LiveData<List<SubBreedWithPhotos>>

    @Query("Select * from subBreeds")
    @Transaction
    fun getAll(): LiveData<List<SubBreedWithPhotos>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(subBreeds: List<SubBreed>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(subBreeds: SubBreed)
}