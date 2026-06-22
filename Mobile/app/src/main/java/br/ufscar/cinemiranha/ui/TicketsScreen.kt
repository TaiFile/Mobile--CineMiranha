package br.ufscar.cinemiranha.ui

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.model.SessionResponse
import br.ufscar.cinemiranha.ui.components.Stepper
import br.ufscar.cinemiranha.viewmodel.CheckoutViewModel
import br.ufscar.cinemiranha.viewmodel.SessionsViewModel
import coil.compose.AsyncImage

private val SBg = Color(0xFF1F2024)
private val SSurface = Color(0xFF2F3036)
private val SRed = Color(0xFFBF0903)
private val SPrimary = Color(0xFFFAFAFA)
private val SSecond = Color(0xFF8F9098)
private val SDivider = Color(0xFF494A50)

@Composable
fun TicketsScreen(
    movieId: Long,
    sessionId: Long,
    checkoutViewModel: CheckoutViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val vm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
    val state by vm.uiState.collectAsState()
    val session = state.sessions.find { it.id == sessionId }
    
    val checkoutState by checkoutViewModel.uiState.collectAsState()
    val selectedSeats = checkoutState.selectedSeats

    Scaffold(
        topBar = { TicketsTopBar(onBack = onBack) },
        bottomBar = { TicketsBottomBar() },
        containerColor = SBg
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Stepper(currentStep = 2)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.choose_tickets),
                        color = SPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                if (state.movie != null && session != null) {
                    item { TicketMovieInfo(state.movie!!, session) }
                }

                item {
                    Text(
                        text = stringResource(R.string.selected_seats_label, selectedSeats.size, selectedSeats.joinToString(", ")),
                        color = SPrimary,
                        fontSize = 14.sp
                    )
                }

                item {
                    TicketTypeItem(
                        label = stringResource(R.string.ticket_full),
                        price = "R$40,00",
                        count = checkoutState.fullPriceCount,
                        onIncrease = { 
                            if (checkoutState.fullPriceCount + checkoutState.halfPriceCount < selectedSeats.size) {
                                checkoutViewModel.setTicketCounts(checkoutState.fullPriceCount + 1, checkoutState.halfPriceCount)
                            }
                        },
                        onDecrease = { 
                            if (checkoutState.fullPriceCount > 0) {
                                checkoutViewModel.setTicketCounts(checkoutState.fullPriceCount - 1, checkoutState.halfPriceCount)
                            }
                        }
                    )
                }

                item {
                    TicketTypeItem(
                        label = stringResource(R.string.ticket_half),
                        price = "R$20,00",
                        count = checkoutState.halfPriceCount,
                        onIncrease = { 
                            if (checkoutState.fullPriceCount + checkoutState.halfPriceCount < selectedSeats.size) {
                                checkoutViewModel.setTicketCounts(checkoutState.fullPriceCount, checkoutState.halfPriceCount + 1)
                            }
                        },
                        onDecrease = { 
                            if (checkoutState.halfPriceCount > 0) {
                                checkoutViewModel.setTicketCounts(checkoutState.fullPriceCount, checkoutState.halfPriceCount - 1)
                            }
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = onNext,
                        enabled = (checkoutState.fullPriceCount + checkoutState.halfPriceCount) == selectedSeats.size && selectedSeats.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SSecond, disabledContainerColor = SDivider),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(stringResource(R.string.btn_next), color = SBg, fontWeight = FontWeight.Bold)
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
            .clip(RoundedCornerShape(8.dp))
            .background(SSurface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ConfirmationNumber, null, tint = SPrimary, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = label, color = SPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = price, color = SSecond, fontSize = 14.sp)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onDecrease) {
                Icon(Icons.Default.RemoveCircleOutline, null, tint = SPrimary)
            }
            Text(text = "$count", color = SPrimary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp))
            IconButton(onClick = onIncrease) {
                Icon(Icons.Default.AddCircleOutline, null, tint = SPrimary)
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
                .background(SSurface),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = movie.title.uppercase(),
                color = SPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.movie_duration, (movie.durationInSeconds?.let { it / 60 } ?: 0)),
                color = SSecond,
                fontSize = 12.sp
            )
            Text(
                text = "${session.dateDayLabel()}  ${session.timeLabel()}  ${session.formatLabel()}  ${session.subtitleLabel()}",
                color = SSecond,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun TicketsTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SSurface)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.cd_back), tint = SPrimary)
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.cd_logo),
            modifier = Modifier.height(36.dp),
            colorFilter = ColorFilter.tint(SRed)
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
private fun TicketsBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SSurface)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.cd_logo),
            modifier = Modifier.height(30.dp),
            colorFilter = ColorFilter.tint(SRed)
        )
    }
}
