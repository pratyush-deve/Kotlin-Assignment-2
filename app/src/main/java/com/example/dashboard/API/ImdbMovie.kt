package com.example.dashboard.API

data class ImdbMovie(
    val id: String?,
    val url: String?,
    val primaryTitle: String?,
    val originalTitle: String?,
    val type: String?,
    val description: String?,
    val primaryImage: String?,
    val releaseDate: String?,
    val runtimeMinutes: Int?,
    val genres: List<String>?,
    val countriesOfOrigin: List<String>?,
    val spokenLanguages: List<String>?,
    val productionCompanies: List<ProductionCompany>?,
    val budget: Int?,
    val grossWorldwide: Int?,
    val averageRating: Double?,
    val numVotes: Int?
)

data class ProductionCompany(
    val id: String?,
    val name: String?
)

