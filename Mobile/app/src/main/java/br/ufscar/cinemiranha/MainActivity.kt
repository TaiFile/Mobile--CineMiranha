package br.ufscar.cinemiranha

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.ufscar.cinemiranha.ui.screens.HomeScreen
import br.ufscar.cinemiranha.ui.screens.MovieDetailScreen
import br.ufscar.cinemiranha.ui.screens.SessionsScreen
import br.ufscar.cinemiranha.ui.screens.SeatsScreen
import br.ufscar.cinemiranha.ui.screens.TicketsScreen
import br.ufscar.cinemiranha.ui.screens.SnacksScreen
import br.ufscar.cinemiranha.ui.screens.OrderSummaryScreen
import br.ufscar.cinemiranha.ui.screens.PaymentMethodScreen
import br.ufscar.cinemiranha.ui.screens.CardDetailsScreen
import br.ufscar.cinemiranha.ui.screens.SuccessScreen
import br.ufscar.cinemiranha.ui.theme.CineMiranhaTheme
import br.ufscar.cinemiranha.viewmodel.CheckoutViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineMiranhaTheme {
                val navController = rememberNavController()

                val checkoutViewModel: CheckoutViewModel = viewModel()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            onMovieClick = { movieId ->
                                checkoutViewModel.resetCheckout()
                                navController.navigate("movie/$movieId")
                            }
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
                            onNext = {
                                navController.navigate("snacks/$movieId/$sessionId")
                            }
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
                            onNext = {
                                navController.navigate("summary/$movieId/$sessionId")
                            }
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
                            onNext = {
                                navController.navigate("payment")
                            }
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
                    composable(route = "card_details") {
                        CardDetailsScreen(
                            ticketCount = checkoutViewModel.getTotalTicketCount(),
                            ticketTotal = checkoutViewModel.getTotalTicketPrice(),
                            snackTotal = checkoutViewModel.getTotalSnackPrice(),
                            onBack = { navController.popBackStack() },
                            onConfirm = { navController.navigate("success") }
                        )
                    }
                    composable("success") {
                        SuccessScreen(
                            onBackToMenu = {
                                checkoutViewModel.resetCheckout()
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
