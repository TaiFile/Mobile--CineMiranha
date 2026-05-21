package br.ufscar.cinemiranha

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.ufscar.cinemiranha.ui.HomeScreen
import br.ufscar.cinemiranha.ui.MovieDetailScreen
import br.ufscar.cinemiranha.ui.SessionsScreen
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
                        movieId      = movieId,
                        onBack       = { navController.popBackStack() },
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
                        onBack  = { navController.popBackStack() }
                    )
                }
            }
            }
        }
    }
}
