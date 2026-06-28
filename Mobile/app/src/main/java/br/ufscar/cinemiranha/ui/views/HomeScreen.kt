package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.ui.composable.Home.MovieContent
import br.ufscar.cinemiranha.ui.composable._shared.BottomBar
import br.ufscar.cinemiranha.ui.composable._shared.ErrorState
import br.ufscar.cinemiranha.ui.composable._shared.LoadingState
import br.ufscar.cinemiranha.ui.composable._shared.TopBar

@Composable
fun HomeScreen(
    isLoading: Boolean,
    errorMessage: String?,
    nowPlaying: List<MovieResponse>,
    comingSoon: List<MovieResponse>,
    onMovieClick: (Long) -> Unit,
    onViewHistory: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                trailingContent = {
                    IconButton(onClick = onViewHistory) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = stringResource(R.string.purchase_history_title),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        },
        bottomBar      = { BottomBar() },
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
                else                 -> MovieContent(
                    nowPlaying   = nowPlaying,
                    comingSoon   = comingSoon,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}
