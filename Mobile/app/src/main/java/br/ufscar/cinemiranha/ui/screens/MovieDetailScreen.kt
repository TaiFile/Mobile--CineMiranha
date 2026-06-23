package br.ufscar.cinemiranha.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.ufscar.cinemiranha.ui.composable.*
import br.ufscar.cinemiranha.ui.composable.MovieDetail.MovieDetail
import br.ufscar.cinemiranha.ui.composable._shared.BottomBar
import br.ufscar.cinemiranha.ui.composable._shared.TopBar
import br.ufscar.cinemiranha.viewmodel.MovieDetailViewModel


@Composable
fun MovieDetailScreen(movieId: Long, onBack: () -> Unit, onBuyTickets: (Long) -> Unit = {}) {
    val viewModel: MovieDetailViewModel = viewModel(factory = MovieDetailViewModel.factory(movieId))
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar    = { TopBar() },
        bottomBar = { BottomBar() },
        containerColor = MaterialTheme.colorScheme.background
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
