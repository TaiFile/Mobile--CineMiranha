package br.ufscar.cinemiranha

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import br.ufscar.cinemiranha.navigation.MainAppNavigation
import br.ufscar.cinemiranha.ui.theme.CineMiranhaTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineMiranhaTheme {
                val navController = rememberNavController()
                MainAppNavigation(navController = navController)
            }
        }
    }
}