package br.ufscar.cinemiranha.ui.composable.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun MovieSection(
    title: String,
    movies: List<MovieResponse>,
    showDuration: Boolean,
    onMovieClick: (Long) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.SpaceL),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleMedium)
            Text(text = stringResource(R.string.see_more), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(Dimens.SpaceM))
        if (movies.isEmpty()) {
            Text(
                text = stringResource(R.string.no_movies),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = Dimens.SpaceL)
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = Dimens.SpaceL),
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceM)
            ) {
                items(movies) { movie ->
                    MovieCard(
                        movie        = movie,
                        showDuration = showDuration,
                        onClick      = { onMovieClick(movie.id) }
                    )
                }
            }
        }
    }
}
