package com.example.movieapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    val id: Long,

    val title: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,

    val overview: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: Date
)