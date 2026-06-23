package br.ufscar.cinemiranha.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import br.ufscar.cinemiranha.viewmodel.HomeViewModel

private val Background  = Color(0xFF1F2024)
private val Surface     = Color(0xFF2F3036)
private val AccentRed   = Color(0xFFBF0903)
private val TextPrimary = Color(0xFFFAFAFA)
private val TextSecond  = Color(0xFF8F9098)
private val AgeBadge    = Color(0xFFFF8C00)

@Composable
fun HomeScreen(
    onMovieClick: (Long) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar    = { TopBar() },
        bottomBar = { BottomBar() },
        containerColor = Background
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
                    onRetry = { viewModel.loadMovies() }
                )
                else -> MovieContent(
                    nowPlaying    = state.nowPlayingMovies,
                    comingSoon    = state.comingSoonMovies,
                    onMovieClick  = onMovieClick
                )
            }
        }
    }
}
