package br.ufscar.cinemiranha.ui.composable.MovieDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun InfoRow(movie: MovieResponse) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val genres = movie.genreNames?.joinToString(", ") ?: ""
        Text(text = genres, color = MaterialTheme.colorScheme.onSecondary, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            movie.ageRatingLabel()?.let { label ->
                AgeBadge(label = label)
                Spacer(modifier = Modifier.width(Dimens.SpaceS))
            }
            movie.durationLabel()?.let { duration ->
                Text(text = duration, color = MaterialTheme.colorScheme.onSecondary, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
