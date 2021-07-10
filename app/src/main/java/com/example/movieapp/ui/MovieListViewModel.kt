package com.example.movieapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.Movie
import com.example.movieapp.data.MovieListRepository
import com.example.movieapp.data.network.LoadingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieListViewModel(application: Application): AndroidViewModel(application){
    private val repo: MovieListRepository =
        MovieListRepository(application)

    val movies: LiveData<List<Movie>> = repo.getMovies()

    private val _loadingStatus= MutableLiveData<LoadingStatus>()
    val loadingStatus:LiveData<LoadingStatus>
        get() = _loadingStatus

    fun fetchFromNetwork() {
        _loadingStatus.value=LoadingStatus.loading()
        viewModelScope.launch {
            _loadingStatus.value= withContext(Dispatchers.IO){
                repo.fetchFromNetwork()
            }
        }
    }

    fun refreshData(){
        viewModelScope.launch (Dispatchers.IO) {
            repo.deleteAllData()
        }

        fetchFromNetwork()
    }
}