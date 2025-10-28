package com.example.dashboard.API

data class ImdbSearchResponse(
    val rows: Int?,
    val numFound: Int?,
    val results: List<SearchMovieItem>?
)

data class SearchMovieItem(
    val id: String?,
    val primaryTitle: String?,
    val originalTitle: String?,
    val type: String?,
    val description: String?,
    val primaryImage: String?,
    val releaseDate: String?,
    val runtimeMinutes: Int?,
    val genres: List<String>?,
    val averageRating: Double?
)

