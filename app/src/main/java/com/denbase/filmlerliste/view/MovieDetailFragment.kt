package com.denbase.filmlerliste.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.denbase.filmlerliste.R
import com.denbase.filmlerliste.util.doPlaceHolder
import com.denbase.filmlerliste.util.downloadImage
import com.denbase.filmlerliste.viewmodel.MovieDetailViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment : Fragment() {

    private lateinit var viewModel : MovieDetailViewModel
    private var movieId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            movieId = MovieDetailFragmentArgs.fromBundle(it).movieId
        }


        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        viewModel.getDataFromRoom(movieId)

        observeLiveData()



    }

    fun observeLiveData(){
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer { movie ->
            movie?.let {

                movieDetailName.text = movie.movieName
                movieDetailCategory.text = movie.movieCategory
                movieDetailIMDB.text = movie.movieIMDB
                movieDetailTime.text = movie.movieTime
                movieDetailSubject.text = movie.movieSubject

                context?.let {
                    movieDetailImage.downloadImage(movie.movieImage, doPlaceHolder(it))
                }
            }
        })
    }

}