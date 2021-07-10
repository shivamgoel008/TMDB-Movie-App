package com.example.movieapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.movieapp.data.network.ErrorCode
import com.example.movieapp.data.network.LoadingStatus
import com.example.movieapp.data.network.TmdbService
import java.lang.Exception
import java.net.UnknownHostException

class MovieListRepository(context: Application){
    private val movieListDao: MovieListDao = MovieDatabase.getDatabase(context).movieListDao()
    private val tmdbService:TmdbService by lazy { TmdbService.getInstance() }

    fun getMovies(): LiveData<List<Movie>> {
        return movieListDao.getMovies()
    }

    suspend fun fetchFromNetwork() =
        try {
            val result=tmdbService.getMovies()
            if(result.isSuccessful){
                val movieList=result.body()
                movieList?.let { movieListDao.insertMovies(it.results) }
                LoadingStatus.success()
            }else{
                LoadingStatus.error(ErrorCode.NO_DATA)
            }
        } catch (ex:UnknownHostException){
            LoadingStatus.error(
                ErrorCode.NETWORK_ERROR
            )
        } catch (ex:Exception){
            LoadingStatus.error(ErrorCode.UNKNOWN_ERROR,ex.message)
        }

    suspend fun deleteAllData(){
        movieListDao.deleteAllData()
    }
}