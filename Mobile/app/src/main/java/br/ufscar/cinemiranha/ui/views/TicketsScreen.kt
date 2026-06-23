package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import br.ufscar.cinemiranha.viewmodel.TicketsViewModel
import coil.compose.AsyncImage

@Composable
fun TicketsScreen(
    movieId: Long,
    sessionId: Long,
    seatsViewModel: SeatsViewModel,
    ticketsViewModel: TicketsViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val vm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
    val state = vm.uiState
    val session = state.sessions.find { it.id == sessionId }

    val seatsState by seatsViewModel.uiState.collectAsState()
    val selectedSeats = seatsState.selectedSeats

    val ticketsState by ticketsViewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TicketsTopBar(onBack = onBack) },
        bottomBar = { TicketsBottomBar() },
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

                if (state.movie != null && session != null) {
                    item { TicketMovieInfo(state.movie!!, session) }
                }

                item {
                    Text(
                        text = stringResource(R.string.selected_seats_label, selectedSeats.size, selectedSeats.joinToString(", ")),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                item {
                    TicketTypeItem(
                        label = stringResource(R.string.ticket_full),
                        price = "R$40,00",
                        count = ticketsState.fullPriceCount,
                        onIncrease = {
                            if (ticketsState.fullPriceCount + ticketsState.halfPriceCount < selectedSeats.size) {
                                ticketsViewModel.setTicketCounts(ticketsState.fullPriceCount + 1, ticketsState.halfPriceCount)
                            }
                        },
                        onDecrease = {
                            if (ticketsState.fullPriceCount > 0) {
                                ticketsViewModel.setTicketCounts(ticketsState.fullPriceCount - 1, ticketsState.halfPriceCount)
                            }
                        }
                    )
                }

                item {
                    TicketTypeItem(
                        label = stringResource(R.string.ticket_half),
                        price = "R$20,00",
                        count = ticketsState.halfPriceCount,
                        onIncrease = {
                            if (ticketsState.fullPriceCount + ticketsState.halfPriceCount < selectedSeats.size) {
                                ticketsViewModel.setTicketCounts(ticketsState.fullPriceCount, ticketsState.halfPriceCount + 1)
                            }
                        },
                        onDecrease = {
                            if (ticketsState.halfPriceCount > 0) {
                                ticketsViewModel.setTicketCounts(ticketsState.fullPriceCount, ticketsState.halfPriceCount - 1)
                            }
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(Dimens.SpaceXXL))
                    Button(
                        onClick = onNext,
                        enabled = (ticketsState.fullPriceCount + ticketsState.halfPriceCount) == selectedSeats.size && selectedSeats.isNotEmpty(),
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

@Composable
private fun TicketTypeItem(label: String, price: String, count: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimens.SpaceL),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ConfirmationNumber, null, tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(Dimens.SpaceM))
            Column {
                Text(text = label, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = price, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onDecrease) {
                Icon(Icons.Default.RemoveCircleOutline, null, tint = MaterialTheme.colorScheme.onBackground)
            }
            Text(text = "$count", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = Dimens.SpaceS))
            IconButton(onClick = onIncrease) {
                Icon(Icons.Default.AddCircleOutline, null, tint = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

@Composable
private fun TicketMovieInfo(movie: MovieResponse, session: SessionResponse) {
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
        Column {
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
                text = "${session.dateDayLabel()}  ${session.timeLabel()}  ${session.formatLabel()}  ${session.subtitleLabel()}",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun TicketsTopBar(onBack: () -> Unit) {
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
private fun TicketsBottomBar() {
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
