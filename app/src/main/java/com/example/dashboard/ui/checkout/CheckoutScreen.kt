package com.example.dashboard.ui.checkout

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dashboard.viewmodel.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Checkout(
    navController: NavController,
    viewModel: UserViewModel
) {
    val selectedCinema = viewModel.selectedCinema.value
    val context = LocalContext.current
    val isDarkTheme by viewModel.isDarkTheme
    val backgroundColor = if (isDarkTheme) Color(0xFF232121) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) White else Color.Black
    val opptextColor = if (isDarkTheme) Color.Black else White
    val oppbackgroundColor = if (isDarkTheme) White else Color.Black
    val selectedSeats = viewModel.selectedSeats.value


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seat Selection", color = White) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = White)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Black,
                contentColor = White,
                actions = {
                    IconButton(onClick = {
                        navController.navigate("Dashboard") {
                            popUpTo("Dashboard") { inclusive = false }
                            launchSingleTop = true
                        }
                        viewModel.resetSeatSelection()
                    }) {
                        Icon(Icons.Default.Home, contentDescription = "Home", tint = White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(
                    color = backgroundColor
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = backgroundColor
                    )
            ){
                Text(
                    text = "Seat Summary",
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                Text(
                    text = "_________________________________",
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = backgroundColor
                    )
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "Selected Seats",
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Price",
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                    )
                }
                if (selectedSeats.isEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No seats selected",
                            color = textColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    selectedSeats.forEach { (row, col) ->
                        val price = when (row) {
                            0 -> 500
                            1, 2 -> 350
                            else -> 200
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "R${row + 1}S${col + 1}",
                                color = textColor
                            )
                            Text(
                                text = "₹$price",
                                color = textColor
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                //total += price of that seat
                //acc = price added, accumulated upto that point
                val total = selectedSeats.fold(0) { acc, (row, _) ->
                    acc + when (row) {
                        0 -> 500
                        1, 2 -> 350
                        else -> 200
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Total",
                        color = textColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "₹$total",
                        color = textColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    Toast.makeText(context, "Booking Confirmed!", Toast.LENGTH_SHORT).show()
                    viewModel.resetSeatSelection()
                    navController.navigate("MovieList"){
                        popUpTo("Dashboard"){inclusive=false}
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC407A))
            ) {
                Text(text = "Confirm Booking", color = White)
            }

        }
    }
}