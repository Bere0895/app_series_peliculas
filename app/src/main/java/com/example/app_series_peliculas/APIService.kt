package com.example.app_series_peliculas

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface APIService {

    @GET
    suspend fun getMovies(@Url url:String) : Response<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id:Int) : Response<MovieDetails>
}