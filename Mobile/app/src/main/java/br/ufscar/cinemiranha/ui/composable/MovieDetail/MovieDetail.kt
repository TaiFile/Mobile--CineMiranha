package br.ufscar.cinemiranha.ui.composable.MovieDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.ui.screens.Divider
import br.ufscar.cinemiranha.ui.screens.HeroSection
import br.ufscar.cinemiranha.ui.screens.InfoRow
import br.ufscar.cinemiranha.ui.screens.Surface
import br.ufscar.cinemiranha.ui.screens.TextPrimary
import br.ufscar.cinemiranha.ui.screens.TextSecond

@Composable
private fun MovieDetail(movie: MovieResponse, onBuyTickets: () -> Unit = {}) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { HeroSection(movie) }
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = movie.title.uppercase(),
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Divider)
                Spacer(modifier = Modifier.height(10.dp))

                InfoRow(movie)

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Divider)
                Spacer(modifier = Modifier.height(16.dp))

                if (!movie.synopsis.isNullOrBlank()) {
                    Text(text = stringResource(R.string.synopsis), color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = movie.synopsis,
                        color = TextSecond,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                Button(
                    onClick = onBuyTickets,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Surface)
                ) {
                    Text(
                        text = stringResource(R.string.buy_tickets),
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}