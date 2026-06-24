package br.ufscar.cinemiranha.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.ufscar.cinemiranha.ui.views.*
import br.ufscar.cinemiranha.viewmodel.CheckoutViewModel
import br.ufscar.cinemiranha.viewmodel.HomeViewModel

@Composable
fun MainAppNavigation(navController: NavHostController) {
    val checkoutViewModel: CheckoutViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
            HomeScreen(
                isLoading = homeViewModel.uiState.isLoading,
                errorMessage = homeViewModel.uiState.errorMessage,
                nowPlaying = homeViewModel.uiState.nowPlayingMovies,
                comingSoon = homeViewModel.uiState.comingSoonMovies,
                onMovieClick = { movieId ->
                    navController.navigate("movie_detail/$movieId")
                },
                onRetry = { homeViewModel.loadMovies() }
            )
        }

        composable(
            route = "movie_detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            MovieDetailScreen(
                movieId = movieId,
                onBack = { navController.popBackStack() },
                onBuyTickets = { navController.navigate("sessions/$movieId") }
            )
        }

        composable(
            route = "sessions/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            SessionsScreen(
                movieId = movieId,
                onBack = { navController.popBackStack() },
                onSessionSelected = { sessionId ->
                    checkoutViewModel.resetCheckout()
                    navController.navigate("seats/$movieId/$sessionId")
                }
            )
        }

        composable(
            route = "seats/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId") { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            SeatsScreen(
                movieId = movieId,
                sessionId = sessionId,
                onBack = { navController.popBackStack() },
                onSeatsSelected = { seats ->
                    checkoutViewModel.setSelectedSeats(seats)
                    navController.navigate("tickets/$movieId/$sessionId")
                }
            )
        }

        composable(
            route = "tickets/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId") { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            TicketsScreen(
                movieId = movieId,
                sessionId = sessionId,
                checkoutViewModel = checkoutViewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate("snacks/$movieId/$sessionId") }
            )
        }

        composable(
            route = "snacks/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId") { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            SnacksScreen(
                viewModel = checkoutViewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate("summary/$movieId/$sessionId") }
            )
        }

        composable(
            route = "summary/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId") { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            OrderSummaryScreen(
                movieId = movieId,
                sessionId = sessionId,
                checkoutViewModel = checkoutViewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate("payment/$movieId/$sessionId") }
            )
        }

        composable(
            route = "payment/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId") { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) {
            PaymentMethodScreen(
                checkoutViewModel = checkoutViewModel,
                onBack = { navController.popBackStack() },
                onSelectMethod = { method ->
                    // Process payment...
                    navController.navigate("success") {
                        popUpTo("home") { inclusive = false }
                    }
                }
            )
        }

        composable("success") {
            SuccessScreen(
                onBackToMenu = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}
