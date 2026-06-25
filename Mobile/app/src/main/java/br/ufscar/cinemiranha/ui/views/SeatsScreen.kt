package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.model.dto.SessionResponse
import br.ufscar.cinemiranha.ui.composable.Seats.SeatGrid
import br.ufscar.cinemiranha.ui.composable.Seats.SeatLegend
import br.ufscar.cinemiranha.ui.composable.Seats.SeatsBottomBar
import br.ufscar.cinemiranha.ui.composable._shared.MovieSessionInfoRow
import br.ufscar.cinemiranha.ui.composable._shared.Stepper
import br.ufscar.cinemiranha.ui.composable._shared.TopBar
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun SeatsScreen(
    movie: MovieResponse?,
    session: SessionResponse?,
    selectedSeats: List<String>,
    onSeatToggle: (String) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Scaffold(
        topBar = { TopBar(onBack = onBack) },
        bottomBar = { SeatsBottomBar(selectedSeats.size, onConfirm = onNext) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Stepper(currentStep = 1)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = Dimens.SpaceL)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.choose_seats),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(Dimens.SpaceL).fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                if (movie != null && session != null) {
                    item {
                        MovieSessionInfoRow(
                            movie = movie,
                            session = session,
                            modifier = Modifier.padding(Dimens.SpaceL)
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier.padding(Dimens.SpaceL),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.outline),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(stringResource(R.string.seats_view), color = MaterialTheme.colorScheme.onBackground)
                        }

                        Spacer(modifier = Modifier.height(Dimens.SpaceXL))

                        SeatGrid(selectedSeats.toSet()) { seat ->
                            onSeatToggle(seat)
                        }

                        Spacer(modifier = Modifier.height(Dimens.SpaceXL))

                        SeatLegend()
                    }
                }
            }
        }
    }
}
