package br.ufscar.cinemiranha.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.model.dto.SessionResponse
import br.ufscar.cinemiranha.ui.composable._shared.Stepper
import br.ufscar.cinemiranha.ui.theme.Dimens
import br.ufscar.cinemiranha.viewmodel.SeatsViewModel
import br.ufscar.cinemiranha.viewmodel.SessionsViewModel
import br.ufscar.cinemiranha.viewmodel.SnacksViewModel
import br.ufscar.cinemiranha.viewmodel.TicketsViewModel
import coil.compose.AsyncImage
import java.util.Locale

@Composable
fun OrderSummaryScreen(
    movieId: Long,
    sessionId: Long,
    seatsViewModel: SeatsViewModel,
    ticketsViewModel: TicketsViewModel,
    snacksViewModel: SnacksViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val sessionsVm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
    val sessionsState by sessionsVm.uiState.collectAsState()
    val session = sessionsState.sessions.find { it.id == sessionId }

    val seatsState by seatsViewModel.uiState.collectAsState()
    val ticketsState by ticketsViewModel.uiState.collectAsState()
    val snacksState by snacksViewModel.uiState.collectAsState()

    Scaffold(
        topBar = { SummaryTopBar(onBack = onBack) },
        bottomBar = { SummaryBottomBar() },
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

                if (sessionsState.movie != null && session != null) {
                    item {
                        SummaryMovieInfo(sessionsState.movie!!, session)
                    }

                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpaceS)) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                            SummaryInfoRow(stringResource(R.string.summary_location), stringResource(R.string.summary_location_value))
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                            SummaryInfoRow(stringResource(R.string.summary_date_time), "${session.dateDayLabel()}  ${session.timeLabel()}")
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

                            val ticketTypeDesc = buildString {
                                if (ticketsState.fullPriceCount > 0) append("${ticketsState.fullPriceCount} ${stringResource(R.string.ticket_full)}")
                                if (ticketsState.fullPriceCount > 0 && ticketsState.halfPriceCount > 0) append(", ")
                                if (ticketsState.halfPriceCount > 0) append("${ticketsState.halfPriceCount} ${stringResource(R.string.ticket_half)}")
                            }
                            SummaryInfoRow(stringResource(R.string.summary_seats), "${seatsState.selectedSeats.joinToString(", ")} ($ticketTypeDesc)")

                            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

                            val snacksSummary = if (snacksState.selectedSnacks.isEmpty()) {
                                "---"
                            } else {
                                snacksState.selectedSnacks.mapNotNull { (id, qty) ->
                                    snacksViewModel.availableSnacks.find { it.id == id }?.let { "${qty}x ${it.name}" }
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
                        ticketCount = ticketsState.fullPriceCount + ticketsState.halfPriceCount,
                        ticketTotal = ticketsViewModel.getTotalPrice(),
                        snackTotal = snacksViewModel.getTotalSnackPrice()
                    )
                }

                item {
                    Button(
                        onClick = onNext,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.ButtonHeight),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(stringResource(R.string.btn_confirm_purchase), color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderTotalCard(ticketCount: Int, ticketTotal: Float, snackTotal: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
            .padding(Dimens.SpaceL)
    ) {
        Text(stringResource(R.string.order_summary_title), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(Dimens.SpaceS))
        HorizontalDivider(color = Color.Black.copy(alpha = 0.1f))

        SummaryPriceRow(stringResource(R.string.summary_tickets_count, ticketCount), "R$ ${String.format(Locale.getDefault(), "%.2f", ticketTotal)}")
        SummaryPriceRow(stringResource(R.string.summary_snackbar_label), "R$ ${String.format(Locale.getDefault(), "%.2f", snackTotal)}")
        SummaryPriceRow(stringResource(R.string.summary_discount), "R$ 0,00")

        HorizontalDivider(color = Color.Black.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = Dimens.SpaceS))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.summary_total), color = Color.Black, fontWeight = FontWeight.Bold)
            Text("R$ ${String.format(Locale.getDefault(), "%.2f", ticketTotal + snackTotal)}", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SummaryPriceRow(label: String, price: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.SpaceS),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodyLarge)
        Text(text = price, color = Color.Black, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun SummaryInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.width(100.dp))
        Spacer(modifier = Modifier.width(Dimens.SpaceS))
        Text(text = value, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun SummaryMovieInfo(movie: MovieResponse, session: SessionResponse) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = movie.coverUrl,
            contentDescription = movie.title,
            modifier = Modifier
                .width(56.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(Dimens.SpaceM))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movie.title.uppercase(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stringResource(R.string.movie_duration, (movie.durationInSeconds?.let { it / 60 } ?: 0)),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "${session.formatLabel()}  ${session.subtitleLabel()}",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.cd_remove), tint = MaterialTheme.colorScheme.secondary)
    }
}

@Composable
private fun SummaryTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = Dimens.SpaceS, vertical = Dimens.SpaceM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.cd_back), tint = MaterialTheme.colorScheme.onBackground)
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.cd_logo),
            modifier = Modifier.height(36.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(Dimens.ButtonHeight))
    }
}

@Composable
private fun SummaryBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.cd_logo),
            modifier = Modifier.height(30.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}
