 package com.example.app_series_peliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_series_peliculas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.TextView


 class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

     private lateinit var  binding: ActivityMainBinding
     private lateinit var  adapter: MovieAdapter
     private val movieImage = mutableListOf<String>()
     private val titleMovie = mutableListOf<String>()
     private val popularList = mutableListOf<CarouselItem>()
     private val nowPlayingList = mutableListOf<CarouselItem>()

     private val API_KEY = "10f49473a31d0be9409a6e3bfc7dc046"
     private val BASE_URL = "https://api.themoviedb.org/3/"
     private val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPopularMovies()
        getNowPlaying()
        binding.svMovie.setOnQueryTextListener(this)
        initRecyclerView()
    }

     private fun initRecyclerView() {
         adapter = MovieAdapter(movieImage)
        binding.rvMovie.layoutManager = LinearLayoutManager(this)
         binding.rvMovie.adapter = adapter
     }

     private fun getRetrofit (): Retrofit {
          return Retrofit.Builder()
              .baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .build()
     }

     private fun getNowPlaying(){
         CoroutineScope(Dispatchers.IO).launch {
             val call = getRetrofit().create(APIService :: class.java).getMovies("movie/now_playing?api_key=$API_KEY")
             val moviesPlaying = call.body()
             runOnUiThread {
                 if(call.isSuccessful) {
                     moviesPlaying?.results?.let {
                         val linearLayout = binding.layoutTitle
                         val textView:TextView = binding.titleMovie
                         val titMovie = it.map {
                             textView.setText(it.title);

                             //linearLayout.addView(textView);
                         }
                         val images = it.map {
                             POSTER_BASE_URL + it.poster_path
                         }
                         nowPlayingList.clear()
                         val carousel: ImageCarousel = binding.movieNowPlaying
                         images.map { nowPlayingList.add(CarouselItem(it)) }
                         carousel.addData(nowPlayingList)
                         adapter.notifyDataSetChanged()
                     }
                 }else {
                     println("Hubo un error")
                     showError()
                 }
             }
         }
     }

     private fun getPopularMovies(){
         CoroutineScope(Dispatchers.IO).launch {
             val call = getRetrofit().create(APIService :: class.java).getMovies("movie/popular?api_key=$API_KEY")
             val movies = call.body()
             runOnUiThread {
                 if(call.isSuccessful) {
                     movies?.results?.let {
                         val images = it.map { POSTER_BASE_URL + it.poster_path }
                         popularList.clear()
                         val carousel: ImageCarousel = binding.moviePopular
                         images.map { popularList.add(CarouselItem(it)) }
                         carousel.addData(popularList)
                         adapter.notifyDataSetChanged()
                     }
                 }else {
                     println("Hubo un error")
                     showError()
                 }
             }
         }
     }
     private fun searchByName(query: String) {
         CoroutineScope(Dispatchers.IO).launch {
             val call = getRetrofit().create(APIService :: class.java).getMovies("movie/popular?api_key=$API_KEY")
             val movies = call.body()
             runOnUiThread {
                 if(call.isSuccessful) {
                     val result = movies?.results?.let {
                         val images = it.map { POSTER_BASE_URL + it.poster_path }
                         movieImage.clear()
                         movieImage.addAll(images)
                         adapter.notifyDataSetChanged()
                     }
                 }else {
                     println("Hubo un error")
                     showError()
                 }
             }
         }
     }

     private fun showError() {
         Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
     }

     override fun onQueryTextSubmit(query: String?): Boolean {
         if(!query.isNullOrEmpty()) {
             searchByName(query)
         }
         return true
     }

     override fun onQueryTextChange(newText: String?): Boolean {
         return true
     }


 }