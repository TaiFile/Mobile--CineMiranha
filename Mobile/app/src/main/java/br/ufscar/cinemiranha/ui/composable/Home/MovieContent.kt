package br.ufscar.cinemiranha.ui.composable.Home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.ui.screens.MovieSection

@Composable
private fun MovieContent(
    nowPlaying: List<MovieResponse>,
    comingSoon: List<MovieResponse>,
    onMovieClick: (Long) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
        item {
            MovieSection(
                title = stringResource(R.string.now_playing),
                movies       = nowPlaying,
                showDuration = true,
                onMovieClick = onMovieClick
            )
        }
        item { Spacer(modifier = Modifier.height(28.dp)) }
        item {
            MovieSection(
                title = stringResource(R.string.coming_soon),
                movies       = comingSoon,
                showDuration = false,
                onMovieClick = onMovieClick
            )
        }
    }
}