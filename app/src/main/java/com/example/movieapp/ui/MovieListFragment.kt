package com.example.movieapp.ui

import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.data.network.ErrorCode
import com.example.movieapp.data.network.Status
import kotlinx.android.synthetic.main.fragment_movie_list.*
import java.lang.Error


class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this)
            .get(MovieListViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(movie_list){
            adapter = MovieAdapter {
                findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(it)
                )
            }
        }

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            (movie_list.adapter as MovieAdapter).submitList(it)

            if(it.isEmpty()){
                viewModel.fetchFromNetwork()
            }
        })

        viewModel.loadingStatus.observe(viewLifecycleOwner, Observer { loadingStatus->
            when{
                loadingStatus?.status==Status.LOADING->{
                    loading_status.visibility=View.VISIBLE
                    status_error.visibility=View.INVISIBLE
                }

                loadingStatus?.status==Status.SUCCESS->{
                    loading_status.visibility=View.INVISIBLE
                    status_error.visibility=View.INVISIBLE
                }

                loadingStatus?.status==Status.ERROR->{
                    loading_status.visibility =View.INVISIBLE
                    showErrorMessage(loadingStatus.errorCode,loadingStatus.message)
                    status_error.visibility=View.VISIBLE
                }
            }
        })
    }

    private fun showErrorMessage(errorCode: ErrorCode?,message: String?){
        when(errorCode){
            ErrorCode.NO_DATA->status_error.text="No data returned from server. Please try again"
            ErrorCode.NETWORK_ERROR->status_error.text="Error fetching data. PLease check network"
            ErrorCode.UNKNOWN_ERROR->status_error.text="Unknown error. ${message}"
        }
    }


}
