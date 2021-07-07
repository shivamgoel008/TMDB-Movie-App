package com.example.movieapp.data

import android.app.Application
import androidx.lifecycle.LiveData

class MovieListRepository(context: Application){
    private val movieListDao: MovieListDao = MovieDatabase.getDatabase(context).movieListDao()

    fun getMovies(): LiveData<List<Movie>> {
        return movieListDao.getMovies()
    }
}