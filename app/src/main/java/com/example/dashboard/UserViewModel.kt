package com.example.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    var name = mutableStateOf("")
    var email = mutableStateOf("")
    var gender = mutableStateOf("")
    var isDarkTheme = mutableStateOf(true)
    var selectedCinema = mutableStateOf("")


    //Re-Submit button booleans
    var submissionComplete = mutableStateOf(false)
    var colorSubmit = mutableStateOf(Color.Black)
    fun resetSubmissionState() {
        submissionComplete.value = false
        colorSubmit.value = if (isDarkTheme.value) Color.White else Color.Black
    }

    //Seat Selection data
    var selectedSeats = mutableStateOf<List<Pair<Int, Int>>>(emptyList())
    fun toggleSeat(row: Int, col: Int) {
        val current = selectedSeats.value.toMutableList()
        val seat = row to col
        if (current.contains(seat)) {
            current.remove(seat)
        } else {
            current.add(seat)
        }
        selectedSeats.value = current
    }
    fun resetSeatSelection() {
        selectedSeats.value = emptyList()
    }
}
