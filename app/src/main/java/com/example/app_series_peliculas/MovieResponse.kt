package com.example.app_series_peliculas

import com.google.gson.annotations.SerializedName

data  class MovieResponse(@SerializedName("page") var page: String,
                          @SerializedName("total_pages") var total_pages: String,
                          @SerializedName("total_results") var total_results: String,
                          @SerializedName("results") var results: List<MovieResult>

                          )
data class MovieResult(@SerializedName("adult") var adult: Boolean,
                       @SerializedName("backdrop_path") var backdrop_path: String?,
                       @SerializedName("genre_ids") var genre_ids: List<Int>,
                       @SerializedName("id") var id: Int,
                       @SerializedName("original_language") var original_language: String,
                       @SerializedName("original_title") var original_title: String,
                       @SerializedName("overview") var overview: String,
                       @SerializedName("popularity") var popularity: Double,
                       @SerializedName("poster_path") var poster_path: String?,
                       @SerializedName("release_date") var release_date: String,
                       @SerializedName("title") var title: String,
                       @SerializedName("video") var video: Boolean,
                       @SerializedName("vote_average") var vote_average: Double,
                       @SerializedName("vote_count") var vote_count: Int)