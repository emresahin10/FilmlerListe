package com.denbase.filmlerliste.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.denbase.filmlerliste.R
import com.denbase.filmlerliste.adapter.MovieRecyclerAdapter
import com.denbase.filmlerliste.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment() {

    private lateinit var viewModel : MovieListViewModel
    private val recyclerMovieAdapter = MovieRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
        viewModel.refreshData()

        movieListRecycler.layoutManager = LinearLayoutManager(context)
        movieListRecycler.adapter = recyclerMovieAdapter

        swipeRefreshLayout.setOnRefreshListener {
            lottieAnimationView.visibility = View.VISIBLE
            movieErrorMessage.visibility = View.GONE
            movieListRecycler.visibility = View.GONE
            viewModel.refreshFromInternet()

            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()



    }

    fun observeLiveData(){

        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            movies?.let {
                movieListRecycler.visibility = View.VISIBLE
                recyclerMovieAdapter.refreshMovieList(movies)
            }
        })

        viewModel.movieErrorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    movieListRecycler.visibility = View.GONE
                    movieErrorMessage.visibility = View.VISIBLE

                }else{
                    movieErrorMessage.visibility = View.GONE
                }

            }
        })

        viewModel.movieLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {

                if (it){
                    movieListRecycler.visibility = View.GONE
                    movieErrorMessage.visibility = View.GONE
                    lottieAnimationView.visibility = View.VISIBLE
                }else{
                    lottieAnimationView.visibility = View.GONE
                }
            }
        })

    }

}