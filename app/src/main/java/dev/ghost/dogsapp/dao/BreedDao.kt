package dev.ghost.dogsapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ghost.dogsapp.entities.Breed
import dev.ghost.dogsapp.entities.BreedWithSubBreeds

@Dao
interface BreedDao {
    @Query("Select * from breeds")
    @Transaction
    fun getAll(): LiveData<List<BreedWithSubBreeds>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(breeds:List<Breed>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(breed:Breed)
}