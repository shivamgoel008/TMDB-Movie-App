package com.example.movieapp.data

import android.app.Application
import androidx.lifecycle.LiveData

class MovieDetailRepository(context: Application){
    private val movieDetailDao: MovieDetailDao = MovieDatabase.getDatabase(context).movieDetailDao()

    fun getMovie(id: Long): LiveData<Movie> {
        return movieDetailDao.getMovie(id)
    }

}