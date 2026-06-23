package br.ufscar.cinemiranha

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.ufscar.cinemiranha.ui.screens.HomeScreen
import br.ufscar.cinemiranha.ui.screens.MovieDetailScreen
import br.ufscar.cinemiranha.viewmodel.HomeViewModel
import br.ufscar.cinemiranha.ui.screens.SessionsScreen
import br.ufscar.cinemiranha.ui.screens.SeatsScreen
import br.ufscar.cinemiranha.ui.screens.TicketsScreen
import br.ufscar.cinemiranha.ui.screens.SnacksScreen
import br.ufscar.cinemiranha.ui.screens.OrderSummaryScreen
import br.ufscar.cinemiranha.ui.screens.PaymentMethodScreen
import br.ufscar.cinemiranha.ui.screens.SuccessScreen
import br.ufscar.cinemiranha.viewmodel.CheckoutViewModel

@Composable
fun MainAppNavigation(navController: NavHostController) {
    val checkoutViewModel: CheckoutViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
            HomeScreen(
                isLoading    = homeViewModel.uiState.isLoading,
                errorMessage = homeViewModel.uiState.errorMessage,
                nowPlaying   = homeViewModel.uiState.nowPlayingMovies,
                comingSoon   = homeViewModel.uiState.comingSoonMovies,
                onMovieClick = { movieId ->
                    checkoutViewModel.resetCheckout()
                    navController.navigate("movie/$movieId")
                },
                onRetry      = { homeViewModel.loadMovies() }
            )
        }
        composable(
            route = "movie/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments!!.getLong("movieId")
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
            val movieId = backStackEntry.arguments!!.getLong("movieId")
            SessionsScreen(
                movieId = movieId,
                onBack = { navController.popBackStack() },
                onSessionSelected = { sessionId ->
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
            val movieId = backStackEntry.arguments!!.getLong("movieId")
            val sessionId = backStackEntry.arguments!!.getLong("sessionId")
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
            val movieId = backStackEntry.arguments!!.getLong("movieId")
            val sessionId = backStackEntry.arguments!!.getLong("sessionId")
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
            val movieId = backStackEntry.arguments!!.getLong("movieId")
            val sessionId = backStackEntry.arguments!!.getLong("sessionId")
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
            val movieId = backStackEntry.arguments!!.getLong("movieId")
            val sessionId = backStackEntry.arguments!!.getLong("sessionId")
            OrderSummaryScreen(
                movieId = movieId,
                sessionId = sessionId,
                checkoutViewModel = checkoutViewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate("payment") }
            )
        }
        composable(route = "payment") {
            PaymentMethodScreen(
                checkoutViewModel = checkoutViewModel,
                onBack = { navController.popBackStack() },
                onSelectMethod = { method ->
                    if (method == "PIX") {
                        navController.navigate("success")
                    } else {
                        navController.navigate("card_details")
                    }
                }
            )
        }
//        composable(route = "card_details") { }
//        composable("success") {
//            SuccessScreen(
//                onBackToMenu = {
//                    checkoutViewModel.resetCheckout()
//                    navController.navigate("home") {
//                        popUpTo("home") { inclusive = true }
//                    }
//                }
//            )
//        }
    }
}
