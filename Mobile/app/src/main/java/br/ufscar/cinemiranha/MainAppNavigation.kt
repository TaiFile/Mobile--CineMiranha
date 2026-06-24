package br.ufscar.cinemiranha

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.ufscar.cinemiranha.viewmodel.HomeViewModel
import br.ufscar.cinemiranha.viewmodel.MovieDetailViewModel
import br.ufscar.cinemiranha.viewmodel.SessionsViewModel
import br.ufscar.cinemiranha.ui.views.HomeScreen
import br.ufscar.cinemiranha.ui.views.MovieDetailScreen
import br.ufscar.cinemiranha.ui.views.SessionsScreen
import br.ufscar.cinemiranha.ui.views.SeatsScreen
import br.ufscar.cinemiranha.ui.views.TicketsScreen
import br.ufscar.cinemiranha.ui.views.SnacksScreen
import br.ufscar.cinemiranha.ui.screens.OrderSummaryScreen
import br.ufscar.cinemiranha.ui.views.SuccessScreen
import br.ufscar.cinemiranha.viewmodel.SeatsViewModel
import br.ufscar.cinemiranha.viewmodel.TicketsViewModel
import br.ufscar.cinemiranha.viewmodel.SnacksViewModel

@Composable
fun MainAppNavigation(navController: NavHostController) {
    val seatsViewModel: SeatsViewModel = viewModel()
    val ticketsViewModel: TicketsViewModel = viewModel()
    val snacksViewModel: SnacksViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
            HomeScreen(
                isLoading    = homeViewModel.uiState.isLoading,
                errorMessage = homeViewModel.uiState.errorMessage,
                nowPlaying   = homeViewModel.uiState.nowPlayingMovies,
                comingSoon   = homeViewModel.uiState.comingSoonMovies,
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
            val vm: MovieDetailViewModel = viewModel(factory = MovieDetailViewModel.factory(movieId))
            MovieDetailScreen(
                isLoading    = vm.uiState.isLoading,
                errorMessage = vm.uiState.errorMessage,
                movie        = vm.uiState.movie,
                onBuyTickets = { navController.navigate("sessions/$movieId") },
                onRetry      = { vm.loadMovie() }
            )
        }

        composable(
            route = "sessions/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val vm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
            SessionsScreen(
                isLoading          = vm.uiState.isLoading,
                errorMessage       = vm.uiState.errorMessage,
                movie              = vm.uiState.movie,
                sessions           = vm.uiState.sessions,
                selectedDate       = vm.uiState.selectedDate,
                selectedSubtitle   = vm.uiState.selectedSubtitle,
                onSessionSelected  = { sessionId ->
                    seatsViewModel.reset()
                    ticketsViewModel.reset()
                    snacksViewModel.reset()
                    navController.navigate("seats/$movieId/$sessionId")
                },
                onDateSelected     = { vm.selectDate(it) },
                onSubtitleSelected = { vm.selectSubtitle(it) },
                onRetry            = { vm.load() }
            )
        }

        composable(
            route = "seats/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId")   { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId   = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            SeatsScreen(
                movieId        = movieId,
                sessionId      = sessionId,
                seatsViewModel = seatsViewModel,
                onBack         = { navController.popBackStack() },
                onNext         = { navController.navigate("tickets/$movieId/$sessionId") }
            )
        }

        composable(
            route = "tickets/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId")   { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId   = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            TicketsScreen(
                movieId          = movieId,
                sessionId        = sessionId,
                seatsViewModel   = seatsViewModel,
                ticketsViewModel = ticketsViewModel,
                onBack           = { navController.popBackStack() },
                onNext           = { navController.navigate("snacks/$movieId/$sessionId") }
            )
        }
        composable(
            route = "snacks/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId")   { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId   = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            SnacksScreen(
                snacksViewModel = snacksViewModel,
                onBack          = { navController.popBackStack() },
                onNext          = { navController.navigate("summary/$movieId/$sessionId") }
            )
        }

        composable(
            route = "summary/{movieId}/{sessionId}",
            arguments = listOf(
                navArgument("movieId")   { type = NavType.LongType },
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId   = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            OrderSummaryScreen(
                movieId          = movieId,
                sessionId        = sessionId,
                seatsViewModel   = seatsViewModel,
                ticketsViewModel = ticketsViewModel,
                snacksViewModel  = snacksViewModel,
                onBack           = { navController.popBackStack() },
                onNext           = {
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
