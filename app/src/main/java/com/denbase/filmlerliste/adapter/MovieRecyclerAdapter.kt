package com.denbase.filmlerliste.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.denbase.filmlerliste.R
import com.denbase.filmlerliste.model.Movie
import com.denbase.filmlerliste.util.doPlaceHolder
import com.denbase.filmlerliste.util.downloadImage
import com.denbase.filmlerliste.view.MovieListFragmentDirections
import kotlinx.android.synthetic.main.fragment_movie_detail.view.*
import kotlinx.android.synthetic.main.movie_recycler_row.view.*

class MovieRecyclerAdapter(val movieList: ArrayList<Movie>) : RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // RECYCLER WIEW ULAŞIM

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_recycler_row, parent, false)
        return MovieViewHolder(view)

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        holder.itemView.movieName.text = movieList.get(position).movieName
        holder.itemView.categoryName.text = movieList.get(position).movieCategory
        holder.itemView.movieRecyclerListImage.downloadImage(movieList.get(position).movieImage,
                doPlaceHolder(holder.itemView.context))

        holder.itemView.setOnClickListener {

            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun refreshMovieList(newMovieList: List<Movie>) {
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
    }

}