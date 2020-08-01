package dev.ghost.dogsapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.ghost.dogsapp.entities.Breed

@Dao
interface BreedDao {
    @Query("Select * from breeds")
    fun getAll(): LiveData<List<Breed>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(breeds:List<Breed>)
}