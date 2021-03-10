package com.denbase.filmlerliste.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.denbase.filmlerliste.model.Movie

@Database(entities = arrayOf(Movie::class),version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDAO

    companion object {

        @Volatile private var instance : MovieDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){

            instance ?: createDatabase(context).also {
                instance = it
            }
        }

    private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                    MovieDatabase::class.java, "MovieDataBase")
                    .build()
    }
}