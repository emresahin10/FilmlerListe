package com.denbase.filmlerliste.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.denbase.filmlerliste.model.Movie
import com.denbase.filmlerliste.service.MovieDatabase
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application) : BaseViewModel(application) {

    val movieLiveData =  MutableLiveData<Movie>()


    fun getDataFromRoom(uuid: Int){
        launch {

            val dao = MovieDatabase(getApplication()).movieDao()
            val movie = dao.getMovie(uuid)
            movieLiveData.value = movie
        }

    }


}