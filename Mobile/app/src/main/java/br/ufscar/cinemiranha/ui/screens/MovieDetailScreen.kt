package br.ufscar.cinemiranha.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import br.ufscar.cinemiranha.ui.composable.*
import br.ufscar.cinemiranha.viewmodel.MovieDetailViewModel

private val Background  = Color(0xFF1F2024)
private val Surface     = Color(0xFF2F3036)
private val TextPrimary = Color(0xFFFAFAFA)
private val TextSecond  = Color(0xFF8F9098)
private val Divider     = Color(0xFF494A50)

@Composable
fun MovieDetailScreen(movieId: Long, onBack: () -> Unit, onBuyTickets: (Long) -> Unit = {}) {
    val viewModel: MovieDetailViewModel = viewModel(factory = MovieDetailViewModel.factory(movieId))
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar    = { CineTopBar(onBack = onBack) },
        bottomBar = { CineBottomBar() },
        containerColor = Background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> LoadingIndicator()
                state.errorMessage != null -> ErrorView(
                    message = state.errorMessage!!,
                    onRetry = { viewModel.loadMovie() }
                )
                state.movie != null -> MovieDetail(movie = state.movie!!, onBuyTickets = { onBuyTickets(movieId) })
            }
        }
    }
}
