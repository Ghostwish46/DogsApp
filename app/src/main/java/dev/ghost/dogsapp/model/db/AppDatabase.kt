package dev.ghost.dogsapp.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ghost.dogsapp.model.dao.BreedDao
import dev.ghost.dogsapp.model.dao.BreedPhotoDao
import dev.ghost.dogsapp.model.entities.Breed
import dev.ghost.dogsapp.model.entities.BreedPhoto

@Database(
    entities = [Breed::class, BreedPhoto::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val breedDao: BreedDao
    abstract val breedPhotoDao: BreedPhotoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance =
                INSTANCE
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