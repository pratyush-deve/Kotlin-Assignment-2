package com.example.dashboard.ui.seatselection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dashboard.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeatSelection(
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

    BackHandler {
        // Triggered for both back button AND swipe gestures
        viewModel.resetSeatSelection()
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seat Selection", color = White) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                            viewModel.resetSeatSelection()
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
    ) {padding->
        Text(
            text = "$selectedCinema",
            color = textColor,
            fontWeight = FontWeight.Bold
        )
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(
                    color = backgroundColor
                ),
        ){
            SeatBox(
                viewModel = viewModel,
                navController = navController
            )
        }

    }
}
@Composable
fun SeatBox(
    viewModel: UserViewModel,
    navController: NavController
){
    val selectedCinema = viewModel.selectedCinema.value
    val context = LocalContext.current
    val isDarkTheme by viewModel.isDarkTheme
    val backgroundColor = if (isDarkTheme) Color(0xFF232121) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) White else Color.Black
    val opptextColor = if (isDarkTheme) Color.Black else White
    val oppbackgroundColor = if (isDarkTheme) White else Color.Black
    val scrollState = rememberScrollState()
    var rowIndex = 0
    var seatIndex = 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor
            ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "$selectedCinema",
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = backgroundColor
                )
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(5){rowIndex->
                    SeatCategory(
                        viewModel = viewModel,
                        rowIndex
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .horizontalScroll(scrollState)
                    ){
                        repeat(10){seatIndex->
                            val isSelected = viewModel.selectedSeats.value.contains(rowIndex to seatIndex)
                            val seatColor = if (isSelected) Color(0xFF6ADC39) else Color.Gray

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(seatColor)
                                    .border(
                                        BorderStroke(
                                            2.dp,
                                            color = oppbackgroundColor
                                        )
                                    )
                                    .clickable(
                                        onClick = {
                                            viewModel.toggleSeat(rowIndex, seatIndex)
                                        }
                                    )
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "|________________SCREEN_________________|",
                color = textColor,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                navController.navigate("CheckoutScreen")
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEC407A)
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp
            ),
            shape = RoundedCornerShape(16.dp)
        ){
            Text(
                text = "Checkout",
                color = White
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Arrow Right",
                tint = White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
@Composable
fun SeatCategory(
    viewModel: UserViewModel,
    rowIndex : Int
){
    val context = LocalContext.current
    val isDarkTheme by viewModel.isDarkTheme
    val backgroundColor = if (isDarkTheme) Color(0xFF232121) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) White else Color.Black
    val opptextColor = if (isDarkTheme) Color.Black else White
    val oppbackgroundColor = if (isDarkTheme) White else Color.Black
    val scrollState = rememberScrollState()

    if(rowIndex==0){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = " VIP    (Rs 500/-)",
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Text(
                text = "_________________________________\n",
                color = textColor,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        }
    }
    if(rowIndex==1){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = " Premium    (Rs 350/-)",
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Text(
                text = "_________________________________\n",
                color = textColor,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        }
    }
    if(rowIndex==3){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = " Standard    (Rs 200/-)",
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Text(
                text = "_________________________________\n",
                color = textColor,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}