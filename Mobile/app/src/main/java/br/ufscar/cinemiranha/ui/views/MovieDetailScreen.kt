package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.ui.composable.MovieDetail.MovieDetail
import br.ufscar.cinemiranha.ui.composable._shared.BottomBar
import br.ufscar.cinemiranha.ui.composable._shared.ErrorState
import br.ufscar.cinemiranha.ui.composable._shared.LoadingState
import br.ufscar.cinemiranha.ui.composable._shared.TopBar

@Composable
fun MovieDetailScreen(
    isLoading: Boolean,
    errorMessage: String?,
    movie: MovieResponse?,
    onBuyTickets: () -> Unit,
    onRetry: () -> Unit
) {
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
                isLoading            -> LoadingState()
                errorMessage != null -> ErrorState(message = errorMessage, onRetry = onRetry)
                movie != null        -> MovieDetail(movie = movie, onBuyTickets = onBuyTickets)
            }
        }
    }
}
