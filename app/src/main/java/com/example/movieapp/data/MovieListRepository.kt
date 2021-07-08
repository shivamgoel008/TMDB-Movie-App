package com.example.movieapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.movieapp.data.network.TmdbService

class MovieListRepository(context: Application){
    private val movieListDao: MovieListDao = MovieDatabase.getDatabase(context).movieListDao()
    private val tmdbService:TmdbService by lazy { TmdbService.getInstance() }

    fun getMovies(): LiveData<List<Movie>> {
        return movieListDao.getMovies()
    }

    suspend fun fetchFromNetwork() {
        val result=tmdbService.getMovies()
        if(result.isSuccessful){
            val movieList=result.body()
            movieList?.let { movieListDao.insertMovies(it.results) }
        }
    }
}