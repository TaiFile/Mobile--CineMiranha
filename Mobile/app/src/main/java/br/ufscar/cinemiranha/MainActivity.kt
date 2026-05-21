package br.ufscar.cinemiranha

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.ufscar.cinemiranha.ui.HomeScreen
import br.ufscar.cinemiranha.ui.MovieDetailScreen
import br.ufscar.cinemiranha.ui.SessionsScreen
import br.ufscar.cinemiranha.ui.SeatsScreen
import br.ufscar.cinemiranha.ui.TicketsScreen
import br.ufscar.cinemiranha.ui.SnacksScreen
import br.ufscar.cinemiranha.ui.OrderSummaryScreen
import br.ufscar.cinemiranha.ui.PaymentMethodScreen
import br.ufscar.cinemiranha.ui.CardDetailsScreen
import br.ufscar.cinemiranha.ui.SuccessScreen
import br.ufscar.cinemiranha.ui.theme.CineMiranhaTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineMiranhaTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            onMovieClick = { movieId ->
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
                                val seatsStr = seats.joinToString(",")
                                navController.navigate("tickets/$movieId/$sessionId/$seatsStr")
                            }
                        )
                    }
                    composable(
                        route = "tickets/{movieId}/{sessionId}/{seats}",
                        arguments = listOf(
                            navArgument("movieId") { type = NavType.LongType },
                            navArgument("sessionId") { type = NavType.LongType },
                            navArgument("seats") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val movieId = backStackEntry.arguments!!.getLong("movieId")
                        val sessionId = backStackEntry.arguments!!.getLong("sessionId")
                        val seats = backStackEntry.arguments!!.getString("seats")?.split(",") ?: emptyList()
                        TicketsScreen(
                            movieId = movieId,
                            sessionId = sessionId,
                            selectedSeats = seats,
                            onBack = { navController.popBackStack() },
                            onNext = { full, half ->
                                navController.navigate("snacks/$movieId/$sessionId/${seats.joinToString(",")}/$full/$half")
                            }
                        )
                    }
                    composable(
                        route = "snacks/{movieId}/{sessionId}/{seats}/{full}/{half}",
                        arguments = listOf(
                            navArgument("movieId") { type = NavType.LongType },
                            navArgument("sessionId") { type = NavType.LongType },
                            navArgument("seats") { type = NavType.StringType },
                            navArgument("full") { type = NavType.IntType },
                            navArgument("half") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val movieId = backStackEntry.arguments!!.getLong("movieId")
                        val sessionId = backStackEntry.arguments!!.getLong("sessionId")
                        val seats = backStackEntry.arguments!!.getString("seats")?.split(",") ?: emptyList()
                        val full = backStackEntry.arguments!!.getInt("full")
                        val half = backStackEntry.arguments!!.getInt("half")
                        SnacksScreen(
                            onBack = { navController.popBackStack() },
                            onNext = {
                                navController.navigate("summary/$movieId/$sessionId/${seats.joinToString(",")}/$full/$half")
                            }
                        )
                    }
                    composable(
                        route = "summary/{movieId}/{sessionId}/{seats}/{full}/{half}",
                        arguments = listOf(
                            navArgument("movieId") { type = NavType.LongType },
                            navArgument("sessionId") { type = NavType.LongType },
                            navArgument("seats") { type = NavType.StringType },
                            navArgument("full") { type = NavType.IntType },
                            navArgument("half") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val movieId = backStackEntry.arguments!!.getLong("movieId")
                        val sessionId = backStackEntry.arguments!!.getLong("sessionId")
                        val seats = backStackEntry.arguments!!.getString("seats")?.split(",") ?: emptyList()
                        val full = backStackEntry.arguments!!.getInt("full")
                        val half = backStackEntry.arguments!!.getInt("half")
                        OrderSummaryScreen(
                            movieId = movieId,
                            sessionId = sessionId,
                            selectedSeats = seats,
                            fullPriceCount = full,
                            halfPriceCount = half,
                            onBack = { navController.popBackStack() },
                            onNext = {
                                val total = (full * 40 + half * 20).toFloat()
                                navController.navigate("payment/$total/0")
                            }
                        )
                    }
                    composable(
                        route = "payment/{ticketTotal}/{snackTotal}",
                        arguments = listOf(
                            navArgument("ticketTotal") { type = NavType.FloatType },
                            navArgument("snackTotal") { type = NavType.FloatType }
                        )
                    ) { backStackEntry ->
                        val ticketTotal = backStackEntry.arguments!!.getFloat("ticketTotal")
                        val snackTotal = backStackEntry.arguments!!.getFloat("snackTotal")
                        PaymentMethodScreen(
                            ticketTotal = ticketTotal,
                            snackTotal = snackTotal,
                            onBack = { navController.popBackStack() },
                            onSelectMethod = { method ->
                                if (method == "PIX") {
                                    navController.navigate("success")
                                } else {
                                    navController.navigate("card_details/$ticketTotal/$snackTotal")
                                }
                            }
                        )
                    }
                    composable(
                        route = "card_details/{ticketTotal}/{snackTotal}",
                        arguments = listOf(
                            navArgument("ticketTotal") { type = NavType.FloatType },
                            navArgument("snackTotal") { type = NavType.FloatType }
                        )
                    ) { backStackEntry ->
                        val ticketTotal = backStackEntry.arguments!!.getFloat("ticketTotal")
                        val snackTotal = backStackEntry.arguments!!.getFloat("snackTotal")
                        CardDetailsScreen(
                            ticketTotal = ticketTotal,
                            snackTotal = snackTotal,
                            onBack = { navController.popBackStack() },
                            onConfirm = { navController.navigate("success") }
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
        }
    }
}
