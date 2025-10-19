package com.example.dashboard

import android.R.attr.icon
import com.example.dashboard.R
import android.R.attr.navigationIcon
import android.content.ClipData
import android.icu.util.Currency.isAvailable
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.nio.file.WatchEvent

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
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val opptextColor = if (isDarkTheme) Color.Black else Color.White
    val oppbackgroundColor = if (isDarkTheme) Color.White else Color.Black
    DisposableEffect(Unit) {//Resets seats list
        onDispose {
            viewModel.resetSeatSelection()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seat Selection", color = Color.White) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                            viewModel.resetSeatSelection()
                        },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
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
                contentColor = Color.White,
                actions = {
                    IconButton(onClick = {
                        navController.navigate("Dashboard") {
                            popUpTo("Dashboard") { inclusive = false }
                            launchSingleTop = true
                        }
                        viewModel.resetSeatSelection()
                    }) {
                        Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White)
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
        }
    }
}