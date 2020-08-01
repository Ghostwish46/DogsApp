package dev.ghost.dogsapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.ghost.dogsapp.entities.SubBreed

@Dao
interface SubBreedDao {
    @Query("Select * from subBreeds")
    fun getAll(): LiveData<List<SubBreed>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(subBreeds:List<SubBreed>)
}