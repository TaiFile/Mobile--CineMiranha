package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import br.ufscar.cinemiranha.viewmodel.SessionsViewModel
import br.ufscar.cinemiranha.ui.theme.Dimens
import coil.compose.AsyncImage

@Composable
fun SeatsScreen(movieId: Long, sessionId: Long, onBack: () -> Unit, onSeatsSelected: (List<String>) -> Unit) {
    val vm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
    val state  = vm.uiState
    val session = state.sessions.find { it.id == sessionId }

    var selectedSeats by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = { SeatsTopBar(onBack = onBack) },
        bottomBar = { SeatsBottomBar(selectedSeats.size, onConfirm = { onSeatsSelected(selectedSeats.toList()) }) },
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

                if (state.movie != null && session != null) {
                    item { SeatMovieInfo(state.movie!!, session) }
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

                        // Mock grid of seats
                        SeatGrid(selectedSeats) { seat ->
                            if (selectedSeats.contains(seat)) {
                                selectedSeats = selectedSeats - seat
                            } else {
                                selectedSeats = selectedSeats + seat
                            }
                        }

                        Spacer(modifier = Modifier.height(Dimens.SpaceXL))

                        SeatLegend()
                    }
                }
            }
        }
    }
}

@Composable
private fun SeatGrid(selectedSeats: Set<String>, onSeatToggle: (String) -> Unit) {
    val rows = listOf("A", "B", "C", "D", "E")
    val cols = 8

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .padding(Dimens.SpaceL),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceXS)) {
                for (col in 1..cols) {
                    val seatId = "$row$col"
                    val isOccupied = (row == "B" && col == 4) || (row == "C" && col > 5)
                    val isSpecial = row == "C" && col == 5
                    val isSelected = selectedSeats.contains(seatId)

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                when {
                                    isSelected -> MaterialTheme.colorScheme.primary
                                    isOccupied -> MaterialTheme.colorScheme.outline
                                    isSpecial -> Color.White
                                    else -> MaterialTheme.colorScheme.secondary
                                }
                            )
                            .clickable(!isOccupied) { onSeatToggle(seatId) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isOccupied) {
                            Text("X", color = Color.Black, fontSize = 10.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(Dimens.SpaceXS))
        }

        Spacer(modifier = Modifier.height(Dimens.SpaceL))
        // Screen indicator
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(4.dp)
                .background(MaterialTheme.colorScheme.outline)
        )
    }
}

@Composable
private fun SeatLegend() {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpaceS)) {
        Text(stringResource(R.string.legend), color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        LegendItem(Color.White, stringResource(R.string.legend_best_view))
        LegendItem(MaterialTheme.colorScheme.outline, stringResource(R.string.legend_occupied), isOccupied = true)
        LegendItem(MaterialTheme.colorScheme.primary, stringResource(R.string.legend_selected))
    }
}

@Composable
private fun LegendItem(color: Color, text: String, isOccupied: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color)
                .border(1.dp, MaterialTheme.colorScheme.outline),
            contentAlignment = Alignment.Center
        ) {
            if (isOccupied) Text("X", color = Color.Black, fontSize = 8.sp)
        }
        Spacer(modifier = Modifier.width(Dimens.SpaceS))
        Text(text = text, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun SeatMovieInfo(movie: MovieResponse, session: SessionResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.SpaceL),
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
private fun SeatsTopBar(onBack: () -> Unit) {
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
private fun SeatsBottomBar(selectedCount: Int, onConfirm: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(Dimens.SpaceL)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.outline),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "$selectedCount", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onConfirm,
                enabled = selectedCount > 0,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, disabledContainerColor = MaterialTheme.colorScheme.outline),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .height(Dimens.ButtonHeight)
                    .fillMaxWidth(0.8f)
            ) {
                Text(stringResource(R.string.btn_choose_tickets), color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(Dimens.SpaceS))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.cd_logo),
            modifier = Modifier.height(30.dp).align(Alignment.CenterHorizontally),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}
