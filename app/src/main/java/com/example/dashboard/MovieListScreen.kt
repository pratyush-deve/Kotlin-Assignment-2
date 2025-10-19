package com.example.dashboard

import com.example.dashboard.R
import android.R.attr.navigationIcon
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: UserViewModel
) {
    val context = LocalContext.current
    val isDarkTheme by viewModel.isDarkTheme
    val backgroundColor = if (isDarkTheme) Color(0xFF232121) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val opptextColor = if (isDarkTheme) Color.Black else Color.White
    val oppbackgroundColor = if (isDarkTheme) Color.White else Color.Black

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    "Movie List",
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
                )
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = oppbackgroundColor
                )
        ){

        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = backgroundColor
                )
                .padding(padding)
                .padding(16.dp)
        ) {
            item(){
                MovieRow(
                    imageRes = R.drawable.movie_thamma,
                    title = "Thamma",
                    genre = "Comedy, Horror, Romantic",
                    rating = 8.2,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            item(){
                MovieRow(
                    imageRes = R.drawable.movie_kantara,
                    title = "Kantara: A Legend Chapter-1",
                    genre = "Adventure, Drama, Thriller",
                    rating = 9.3,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            item(){
                MovieRow(
                    imageRes = R.drawable.movie_jolly3,
                    title = "Jolly LLB 3",
                    genre = "Comedy, Drama",
                    rating = 8.2,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            item(){
                MovieRow(
                    imageRes = R.drawable.movie_callog,
                    title = "They Call Him OG",
                    genre = "Action, Crime, Drama, Thriller",
                    rating = 8.9,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            item(){
                MovieRow(
                    imageRes = R.drawable.movie_homebound,
                    title = "Homebound",
                    genre = "Drama",
                    rating = 9.1,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            item(){
                MovieRow(
                    imageRes = R.drawable.movie_shinchan,
                    title = "Shin chan: The Spicy Kasukabe Dancers in India",
                    genre = "Adventure, Anime, Comedy",
                    rating = 8.4,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            item(){
                MovieRow(
                    imageRes = R.drawable.movie_ram,
                    title = "Mahayoddha Rama",
                    genre = "Animation, Drama, Fantasy",
                    rating = 10.0,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}
@Composable
fun MovieRow(
    imageRes: Int, title: String, genre: String, rating: Double,
    viewModel: UserViewModel,
    navController: NavController
){
    val context = LocalContext.current
    val isDarkTheme by viewModel.isDarkTheme
    val backgroundColor = if (isDarkTheme) Color(0xFF232121) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val opptextColor = if (isDarkTheme) Color.Black else Color.White
    val oppbackgroundColor = if (isDarkTheme) Color.White else Color.Black

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate(
                    "MovieDetailScreen/${title}/${genre}/${rating}/${imageRes}"
                )
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = oppbackgroundColor
                )
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = Color.Gray,
                        RoundedCornerShape(12.dp))
            ){
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Movie Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(4.dp))
                        .border(
                            BorderStroke(2.dp, Color.Cyan),
                            RoundedCornerShape(4.dp)
                        ),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column( ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        "🎬 $title",
                        fontWeight = FontWeight.Bold,
                        color = opptextColor
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text = "Genre : ",
                        color = opptextColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                    )
                    Text(
                        "$genre",
                        color = opptextColor
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Rating : ",
                        color = opptextColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                    )
                    Text(
                        "$rating",
                        color = opptextColor
                    )
                }
            }
        }
    }
}