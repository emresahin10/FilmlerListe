package com.denbase.filmlerliste.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.denbase.filmlerliste.model.Movie

@Dao
interface MovieDAO {

    @Insert
    suspend fun insertAll(vararg movie : Movie) : List<Long>


    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movie WHERE uuid =:movieId")
    suspend fun getMovie(movieId : Int) : Movie

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovies()
}