package dev.ghost.dogsapp.helpers

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ghost.dogsapp.dao.BreedDao
import dev.ghost.dogsapp.dao.BreedPhotoDao
import dev.ghost.dogsapp.dao.SubBreedDao
import dev.ghost.dogsapp.dao.SubBreedPhotoDao
import dev.ghost.dogsapp.entities.*

@Database(
    entities = [Breed::class, SubBreed::class, BreedPhoto::class, SubBreedPhoto::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val breedDao: BreedDao
    abstract val subBreedDao: SubBreedDao
    abstract val breedPhotoDao: BreedPhotoDao
    abstract val subBreedPhotoDao: SubBreedPhotoDao

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