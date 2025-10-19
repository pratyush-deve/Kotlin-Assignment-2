package com.example.dashboard

import android.R.attr.icon
import com.example.dashboard.R
import android.R.attr.navigationIcon
import android.content.ClipData
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    navController: NavController,
    viewModel: UserViewModel,
    title: String,
    genre: String,
    rating: String,
    imageRes: Int
){
    val context = LocalContext.current
    val isDarkTheme by viewModel.isDarkTheme
    val backgroundColor = if (isDarkTheme) Color(0xFF232121) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val opptextColor = if (isDarkTheme) Color.Black else Color.White
    val oppbackgroundColor = if (isDarkTheme) Color.White else Color.Black
    var showCinemaList by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    "Movie Details",
                    color = Color.White
                ) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Black,
                contentColor = Color.White,
                actions = {
                    IconButton(onClick = {
                        navController.navigate("Dashboard"){
                            popUpTo("Dashboard"){inclusive=false}//prevents dashboard from being popped
                            launchSingleTop = true//prevents duplicate calling
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        Card(
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            ),
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp),
        ){
            Box(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
            ){
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Movie Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(4.dp))
                        .border(
                            BorderStroke(2.dp, Color.Cyan),
                            RoundedCornerShape(16.dp)
                        ),
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f)
                    .weight(1f)
                    .background(
                        color = oppbackgroundColor
                    ),
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(
                            color = oppbackgroundColor
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "$title",
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            color = opptextColor
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "Genre : ",
                            fontWeight = FontWeight.Bold,
                            color = opptextColor
                        )
                        Text(
                            text = "$genre",
                            color = opptextColor
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "Ratings : ",
                            fontWeight = FontWeight.Bold,
                            color = opptextColor
                        )
                        Text(
                            text = "$rating ⭐",
                            color = opptextColor
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ){
                        items(10){
                            IconButton(
                                onClick = {
                                    Toast
                                        .makeText(context, "Actor Name", Toast.LENGTH_SHORT)
                                        .show()
                                },
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(
                                        Color.Gray,
                                        shape = CircleShape
                                    )
                                    .clip(CircleShape)
                                    .border(
                                        2.dp,
                                        Color.Cyan,
                                        CircleShape
                                    )
                                    .padding(horizontal = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Actor Pic",
                                    tint = White,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ){
                        Text(
                            text = "About the Movie:-",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = opptextColor,
                            style = TextStyle(
                                textDecoration = TextDecoration.Underline
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        item() {
                            Text(
                                text = "Set against a backdrop of emotion and chaos, this movie explores the fragile balance between love, ambition, and destiny. As worlds collide and secrets unfold, each character embarks on a journey that challenges their beliefs and changes their lives forever. With breathtaking cinematography, heart-stirring performances, and a soundtrack that lingers long after the credits roll, this film is a powerful reminder of hope, loss, and the unbreakable human spirit.",
                                color = opptextColor,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
            Button(
                onClick = {
                    showCinemaList = true
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
                    text = "Book Movie",
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Arrow Right",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            if (showCinemaList) {
                AlertDialog(
                    onDismissRequest = { showCinemaList = false },
                    confirmButton = {
                        TextButton(onClick = { showCinemaList = false }) {
                            Text("Cancel", color = Color.Gray)
                        }
                    },
                    title = {
                        Text(
                            text = "Select a Cinema Hall",
                            fontWeight = FontWeight.Bold,
                            color = opptextColor
                        )
                    },
                    text = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val cinemas = listOf(
                                "Esplanade",
                                "Utkal Galleria",
                                "BMC: Bhavani Mall",
                                "Regalia"
                            )
                            cinemas.forEach { cinema ->
                                Button(
                                    onClick = {
                                        viewModel.selectedCinema.value = cinema
                                        showCinemaList = false
                                        Toast.makeText(
                                            context,
                                            "Selected: $cinema",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate("SeatSelection")
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFEC407A)
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = cinema,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    },
                    containerColor = oppbackgroundColor,
                    tonalElevation = 8.dp
                )
            }
        }
    }
}
