package dev.ghost.dogsapp.helpers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ghost.dogsapp.dao.BreedDao
import dev.ghost.dogsapp.dao.SubBreedDao
import dev.ghost.dogsapp.entities.Breed
import dev.ghost.dogsapp.entities.SubBreed

@Database(
    entities = [Breed::class, SubBreed::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val breedDao: BreedDao
    abstract val subBreedDao: SubBreedDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "NotForgotDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}