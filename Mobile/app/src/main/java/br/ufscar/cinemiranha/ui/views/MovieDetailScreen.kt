package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.ufscar.cinemiranha.ui.composable.MovieDetail.MovieDetail
import br.ufscar.cinemiranha.ui.composable._shared.BottomBar
import br.ufscar.cinemiranha.ui.composable._shared.ErrorState
import br.ufscar.cinemiranha.ui.composable._shared.LoadingState
import br.ufscar.cinemiranha.ui.composable._shared.TopBar
import br.ufscar.cinemiranha.viewmodel.MovieDetailViewModel

@Composable
fun MovieDetailScreen(movieId: Long, onBack: () -> Unit, onBuyTickets: (Long) -> Unit = {}) {
    val viewModel: MovieDetailViewModel = viewModel(factory = MovieDetailViewModel.factory(movieId))
    val state = viewModel.uiState

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
                state.isLoading -> LoadingState()
                state.errorMessage != null -> ErrorState(
                    message = state.errorMessage!!,
                    onRetry = { viewModel.loadMovie() }
                )
                state.movie != null -> MovieDetail(movie = state.movie!!, onBuyTickets = { onBuyTickets(movieId) })
            }
        }
    }
}
