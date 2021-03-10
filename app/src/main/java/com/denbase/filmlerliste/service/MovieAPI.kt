package com.denbase.filmlerliste.service

import com.denbase.filmlerliste.model.Movie
import io.reactivex.Single
import retrofit2.http.GET

interface MovieAPI {

    @GET("emresahin10/FilmlerAPIJson/main/filmlerjson.json")

    fun getMovie(): Single<List<Movie>>
}