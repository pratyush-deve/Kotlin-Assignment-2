package com.example.dashboard.ui.movielist

import com.example.dashboard.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dashboard.viewmodel.MovieViewModel
import com.example.dashboard.viewmodel.UserViewModel

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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ){
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
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = {/*todo*/}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Account details",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        val movieViewModel: MovieViewModel = viewModel()
        val results = movieViewModel.movieResults.value
        val query = movieViewModel.searchQuery.value
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    color = backgroundColor
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ){
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .padding(8.dp)

            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = {
                        movieViewModel.searchQuery.value = it
                        if (it.length > 2) { // auto-search when typing
                            movieViewModel.getData(it)

                        }
                    },
                    label = { Text("Search Movie", color = textColor) },
                    placeholder = { Text("Type movie name ") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                        cursorColor = textColor,
                        focusedLabelColor = textColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            movieViewModel.searchMovies(query)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = textColor
                            )
                        }
                    }
                )
                if (results.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .heightIn(max = 300.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        LazyColumn {
                            items(results.take(10)) { movie ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = backgroundColor
                                        )
                                        .clickable {
                                            println("Selected: ${movie.originalTitle}")
                                            movieViewModel.searchQuery.value = movie.originalTitle ?: ""
                                        }
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    //normal image acnt handle imgaes from url
                                    //so Async img loads img from internet
                                    AsyncImage(
                                        model = movie.primaryImage,
                                        contentDescription = movie.originalTitle,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    color = backgroundColor
                                                )
                                        ){
                                            Text(
                                                movie.originalTitle ?: "Unknown",
                                                color = textColor,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    color = backgroundColor
                                                )
                                        ){
                                            if (movie.runtimeMinutes != null) {
                                                Text("${movie.runtimeMinutes} mins", color = textColor.copy(alpha = 0.6f))
                                            }
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    color = backgroundColor
                                                )
                                        ){
                                            if (movie.averageRating != null) {
                                                Text("${movie.averageRating}⭐", color = textColor.copy(alpha = 0.6f))
                                            }
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    color = backgroundColor
                                                )
                                        ){
                                            if (movie.releaseDate != null) {
                                                Text("${movie.releaseDate}", color = textColor.copy(alpha = 0.6f))
                                            }
                                        }
                                    }
                                }
                                Divider()
                            }
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = backgroundColor
                    )
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
            .border(
                width = 3.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Blue,
                        Color.Magenta
                    )
                ),
                shape = RoundedCornerShape(8.dp)
            )
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