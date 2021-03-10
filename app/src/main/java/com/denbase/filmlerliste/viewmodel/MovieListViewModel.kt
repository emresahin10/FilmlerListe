package com.denbase.filmlerliste.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.denbase.filmlerliste.model.Movie
import com.denbase.filmlerliste.service.MovieAPIService
import com.denbase.filmlerliste.service.MovieDatabase
import com.denbase.filmlerliste.util.PrivateSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MovieListViewModel(application: Application) : BaseViewModel(application) {

    val movies = MutableLiveData<List<Movie>>()
    val movieErrorMessage = MutableLiveData<Boolean>()
    val movieLoading = MutableLiveData<Boolean>()

    private val movieApiService = MovieAPIService()
    private val disposable = CompositeDisposable()
    private val privateSharedPreferences = PrivateSharedPreferences(getApplication())
    private var updateTime = 1 * 60 * 1000 * 1000 * 1000L



    fun refreshData(){

        val recordingTime = privateSharedPreferences.getTime()
        if( recordingTime != null && recordingTime != 0L && System.nanoTime() - recordingTime < updateTime){
            getDataFromSQLite()
        }else{
            getDataFromInternet()
        }
    }

    fun refreshFromInternet(){
        getDataFromInternet()
    }

    private fun getDataFromSQLite(){
        movieLoading.value = true
        launch {
            val movieList =  MovieDatabase(getApplication()).movieDao().getAllMovies()
            showMovies(movieList)
        }
    }

    private fun getDataFromInternet(){
        movieLoading.value = true

        disposable.add(
                movieApiService.getData()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Movie>>(){
                            override fun onSuccess(t: List<Movie>) {
                                saveSqlite(t)
                            }
                            override fun onError(e: Throwable) {
                                movieErrorMessage.value = true
                                movieLoading.value = false
                                e.printStackTrace()
                            }
                        })
        )

    }

    private fun showMovies(moviesList: List<Movie>){
        movies.value = moviesList
        movieErrorMessage.value = false
        movieLoading.value = false
    }

    private fun saveSqlite(moviesList: List<Movie>){

        launch {
            val dao  = MovieDatabase(getApplication()).movieDao()
            dao.deleteAllMovies()
            val uuidList =  dao.insertAll(*moviesList.toTypedArray()) // Liste içerisindeki işlemler tek tek movie olarak işlenecek. Başına yıldız koyuldğu için
            var i = 0
            while (i < moviesList.size){
                moviesList[i].uuid = uuidList[i].toInt()
                i += 1
            }
            showMovies(moviesList)
        }
        privateSharedPreferences.saveTime(System.nanoTime())
    }
}