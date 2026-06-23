package br.ufscar.cinemiranha.ui.composable.MovieDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun MovieDetail(movie: MovieResponse, onBuyTickets: () -> Unit = {}) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { HeroSection(movie) }
        item {
            Column(modifier = Modifier.padding(horizontal = Dimens.SpaceL)) {
                Spacer(modifier = Modifier.height(Dimens.SpaceL))

                Text(
                    text = movie.title.uppercase(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(Dimens.SpaceM))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.height(10.dp))

                InfoRow(movie)

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.height(Dimens.SpaceL))

                if (!movie.synopsis.isNullOrBlank()) {
                    Text(text = stringResource(R.string.synopsis), color = MaterialTheme.colorScheme.onPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = movie.synopsis,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(Dimens.SpaceXL))
                }

                Button(
                    onClick = onBuyTickets,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        text = stringResource(R.string.buy_tickets),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.SpaceL))
            }
        }
    }
}
