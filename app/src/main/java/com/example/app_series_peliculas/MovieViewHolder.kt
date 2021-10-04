package com.example.app_series_peliculas

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.app_series_peliculas.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemMovieBinding.bind(view)

    fun bind(image: String) {
        println(image)
        Picasso.get().load(image).into(binding.ivMovie)
    }
}