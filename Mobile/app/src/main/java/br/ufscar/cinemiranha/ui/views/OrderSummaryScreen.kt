package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.Snack
import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.model.dto.SessionResponse
import br.ufscar.cinemiranha.ui.composable.OrderSummary.OrderTotalCard
import br.ufscar.cinemiranha.ui.composable.OrderSummary.SummaryInfoRow
import br.ufscar.cinemiranha.ui.composable._shared.BottomBar
import br.ufscar.cinemiranha.ui.composable._shared.MovieSessionInfoRow
import br.ufscar.cinemiranha.ui.composable._shared.Stepper
import br.ufscar.cinemiranha.ui.composable._shared.TopBar
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun OrderSummaryScreen(
    movie: MovieResponse?,
    session: SessionResponse?,
    selectedSeats: List<String>,
    fullPriceCount: Int,
    halfPriceCount: Int,
    ticketTotal: Float,
    snackTotal: Float,
    selectedSnacks: Map<Int, Int>,
    availableSnacks: List<Snack>,
    isConfirming: Boolean,
    confirmError: String?,
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
            Stepper(currentStep = 4)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Dimens.SpaceL),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpaceL)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.confirm_order),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                if (movie != null && session != null) {
                    item {
                        MovieSessionInfoRow(
                            movie = movie,
                            session = session,
                            showDateTime = false,
                            trailingContent = {
                                Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.cd_remove), tint = MaterialTheme.colorScheme.secondary)
                            }
                        )
                    }

                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpaceS)) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                            SummaryInfoRow(stringResource(R.string.summary_location), stringResource(R.string.summary_location_value))
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                            SummaryInfoRow(stringResource(R.string.summary_date_time), "${session.dateDayLabel()}  ${session.timeLabel()}")
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

                            val ticketTypeDesc = buildString {
                                if (fullPriceCount > 0) append("$fullPriceCount ${stringResource(R.string.ticket_full)}")
                                if (fullPriceCount > 0 && halfPriceCount > 0) append(", ")
                                if (halfPriceCount > 0) append("$halfPriceCount ${stringResource(R.string.ticket_half)}")
                            }
                            SummaryInfoRow(stringResource(R.string.summary_seats), "${selectedSeats.joinToString(", ")} ($ticketTypeDesc)")

                            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

                            val snacksSummary = if (selectedSnacks.isEmpty()) {
                                "---"
                            } else {
                                selectedSnacks.mapNotNull { (id, qty) ->
                                    availableSnacks.find { it.id == id }?.let { "${qty}x ${it.name}" }
                                }.joinToString(", ")
                            }
                            SummaryInfoRow(stringResource(R.string.summary_snackbar), snacksSummary)
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                        }
                    }
                }

                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        Button(
                            onClick = onBack,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(stringResource(R.string.btn_edit_order), color = MaterialTheme.colorScheme.background)
                        }
                    }
                }

                item {
                    OrderTotalCard(
                        ticketCount = fullPriceCount + halfPriceCount,
                        ticketTotal = ticketTotal,
                        snackTotal = snackTotal
                    )
                }

                if (confirmError != null) {
                    item {
                        Text(
                            text = confirmError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    Button(
                        onClick = onNext,
                        enabled = !isConfirming,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.ButtonHeight),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        if (isConfirming) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.background,
                                strokeWidth = androidx.compose.ui.unit.Dp(2f),
                                modifier = Modifier.height(Dimens.SpaceL)
                            )
                        } else {
                            Text(stringResource(R.string.btn_confirm_purchase), color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
