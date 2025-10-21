package com.example.dashboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dashboard.DashboardContent
import com.example.dashboard.ui.checkout.Checkout
import com.example.dashboard.ui.moviedetail.MovieDetailScreen
import com.example.dashboard.ui.movielist.MovieListScreen
import com.example.dashboard.ui.seatselection.SeatSelection
import com.example.dashboard.viewmodel.UserViewModel

@Composable
fun MovieApp(){
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination.route == "Dashboard") {
                // reset submit status
                userViewModel.resetSubmissionState()
            }
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
    NavHost(
        navController = navController,
        startDestination = "Dashboard")
    {
        composable("Dashboard"){
            DashboardContent(navController, userViewModel)
        }
        composable("MovieList"){
            MovieListScreen(navController, userViewModel)
        }
        composable(
            route = "MovieDetailScreen/{title}/{genre}/{rating}/{imageRes}"
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val genre = backStackEntry.arguments?.getString("genre") ?: ""
            val rating = backStackEntry.arguments?.getString("rating") ?: ""
            val imageRes = backStackEntry.arguments?.getString("imageRes")?.toIntOrNull() ?: 0

            MovieDetailScreen(
                navController = navController,
                viewModel = userViewModel,
                title = title,
                genre = genre,
                rating = rating,
                imageRes = imageRes
            )
        }
        composable("SeatSelection"){
            SeatSelection(navController, userViewModel)
        }
        composable("CheckoutScreen"){
            Checkout(navController, userViewModel)
        }
    }
}

