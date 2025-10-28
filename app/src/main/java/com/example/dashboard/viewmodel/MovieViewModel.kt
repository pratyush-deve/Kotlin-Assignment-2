package com.example.dashboard.viewmodel

import android.graphics.Movie
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dashboard.API.ImdbMovie
import com.example.dashboard.API.RetrofitInstance
import com.example.dashboard.API.SearchMovieItem
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    var searchQuery = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)
    var movieResults = mutableStateOf<List<SearchMovieItem>>(emptyList())
    var selectedMovie = mutableStateOf<ImdbMovie?>(null)
    private val api = RetrofitInstance.api

    fun getData(
        query: String
    ) {
        viewModelScope.launch {
            val response = api.searchMovies(query)
            if (response.isSuccessful) {
                movieResults.value = response.body()?.results ?: emptyList()
            } else {
                Log.i("Error :", response.message())
            }
            Log.d("API_URL", response.raw().request.url.toString())
            Log.d("API_CODE", response.code().toString())
            Log.d("API_JSON", response.body().toString())


        }

    }

    fun searchMovies(searchquery: String) {
        viewModelScope.launch {
            if (searchquery.isBlank()) return@launch
            isLoading.value = true
            errorMessage.value = null
            try {
                val response = api.searchMovies(searchquery)
                if (response.isSuccessful) {
                    movieResults.value = response.body()?.results ?: emptyList()

                } else {
                    errorMessage.value = response.message()
                }
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }
}