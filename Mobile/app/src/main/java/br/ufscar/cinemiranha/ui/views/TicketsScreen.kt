package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.model.dto.SessionResponse
import br.ufscar.cinemiranha.ui.composable.Tickets.TicketTypeItem
import br.ufscar.cinemiranha.ui.composable._shared.BottomBar
import br.ufscar.cinemiranha.ui.composable._shared.MovieSessionInfoRow
import br.ufscar.cinemiranha.ui.composable._shared.Stepper
import br.ufscar.cinemiranha.ui.composable._shared.TopBar
import br.ufscar.cinemiranha.ui.theme.Dimens
import java.util.Locale

@Composable
fun TicketsScreen(
    movie: MovieResponse?,
    session: SessionResponse?,
    selectedSeatsCount: Int,
    selectedSeatsLabel: String,
    fullPriceCount: Int,
    halfPriceCount: Int,
    fullPrice: Float,
    halfPrice: Float,
    onTicketCountsChanged: (Int, Int) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Scaffold(
        topBar = { TopBar(onBack = onBack) },
        bottomBar = { BottomBar() },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Stepper(currentStep = 2)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Dimens.SpaceL),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpaceL)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.choose_tickets),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                if (movie != null && session != null) {
                    item { MovieSessionInfoRow(movie = movie, session = session) }
                }

                item {
                    Text(
                        text = stringResource(R.string.selected_seats_label, selectedSeatsCount, selectedSeatsLabel),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                item {
                    TicketTypeItem(
                        label = stringResource(R.string.ticket_full),
                        price = "R$ ${String.format(Locale.getDefault(), "%.2f", fullPrice)}",
                        count = fullPriceCount,
                        onIncrease = {
                            if (fullPriceCount + halfPriceCount < selectedSeatsCount) {
                                onTicketCountsChanged(fullPriceCount + 1, halfPriceCount)
                            }
                        },
                        onDecrease = {
                            if (fullPriceCount > 0) {
                                onTicketCountsChanged(fullPriceCount - 1, halfPriceCount)
                            }
                        }
                    )
                }

                item {
                    TicketTypeItem(
                        label = stringResource(R.string.ticket_half),
                        price = "R$ ${String.format(Locale.getDefault(), "%.2f", halfPrice)}",
                        count = halfPriceCount,
                        onIncrease = {
                            if (fullPriceCount + halfPriceCount < selectedSeatsCount) {
                                onTicketCountsChanged(fullPriceCount, halfPriceCount + 1)
                            }
                        },
                        onDecrease = {
                            if (halfPriceCount > 0) {
                                onTicketCountsChanged(fullPriceCount, halfPriceCount - 1)
                            }
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(Dimens.SpaceXXL))
                    Button(
                        onClick = onNext,
                        enabled = (fullPriceCount + halfPriceCount) == selectedSeatsCount && selectedSeatsCount > 0,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.ButtonHeight),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, disabledContainerColor = MaterialTheme.colorScheme.outline),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(stringResource(R.string.btn_next), color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
