package br.ufscar.cinemiranha.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.model.SessionResponse
import br.ufscar.cinemiranha.ui.components.Stepper
import br.ufscar.cinemiranha.viewmodel.SessionsViewModel
import coil.compose.AsyncImage

private val SBg = Color(0xFF1F2024)
private val SSurface = Color(0xFF2F3036)
private val SRed = Color(0xFFBF0903)
private val SPrimary = Color(0xFFFAFAFA)
private val SSecond = Color(0xFF8F9098)
private val SDivider = Color(0xFF494A50)

@Composable
fun SeatsScreen(movieId: Long, sessionId: Long, onBack: () -> Unit, onSeatsSelected: (List<String>) -> Unit) {
    val vm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
    val state by vm.uiState.collectAsState()
    val session = state.sessions.find { it.id == sessionId }
    
    var selectedSeats by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = { SeatsTopBar(onBack = onBack) },
        bottomBar = { SeatsBottomBar(selectedSeats.size, onConfirm = { onSeatsSelected(selectedSeats.toList()) }) },
        containerColor = SBg
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Stepper(currentStep = 1)
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    Text(
                        text = "Escolha seus assentos",
                        color = SPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                if (state.movie != null && session != null) {
                    item { SeatMovieInfo(state.movie!!, session) }
                }

                item {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = SDivider),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Visão dos assentos", color = SPrimary)
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Mock grid of seats
                        SeatGrid(selectedSeats) { seat ->
                            if (selectedSeats.contains(seat)) {
                                selectedSeats = selectedSeats - seat
                            } else {
                                selectedSeats = selectedSeats + seat
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
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
            .background(SSurface, RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
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
                                    isSelected -> SRed
                                    isOccupied -> SDivider
                                    isSpecial -> Color.White
                                    else -> SSecond
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
            Spacer(modifier = Modifier.height(4.dp))
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        // Screen indicator
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(4.dp)
                .background(SDivider)
        )
    }
}

@Composable
private fun SeatLegend() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Legenda", color = SPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        LegendItem(Color.White, "Assentos com melhor visão de leitura de legenda", isSpecial = true)
        LegendItem(SDivider, "Assentos ocupados", isOccupied = true)
        LegendItem(SRed, "Assentos selecionados")
    }
}

@Composable
private fun LegendItem(color: Color, text: String, isOccupied: Boolean = false, isSpecial: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color)
                .border(1.dp, SDivider),
            contentAlignment = Alignment.Center
        ) {
            if (isOccupied) Text("X", color = Color.Black, fontSize = 8.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = SSecond, fontSize = 12.sp)
    }
}

@Composable
private fun SeatMovieInfo(movie: MovieResponse, session: SessionResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
                text = "Duração do filme: ${movie.durationInSeconds?.let { it / 60 } ?: 0} minutos",
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
private fun SeatsTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SSurface)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = SPrimary)
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.height(36.dp),
            colorFilter = ColorFilter.tint(SRed)
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
private fun SeatsBottomBar(selectedCount: Int, onConfirm: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SSurface)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(SDivider),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "$selectedCount", color = SPrimary, fontWeight = FontWeight.Bold)
            }
            
            Button(
                onClick = onConfirm,
                enabled = selectedCount > 0,
                colors = ButtonDefaults.buttonColors(containerColor = SSecond, disabledContainerColor = SDivider),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(0.8f)
            ) {
                Text("ESCOLHER INGRESSOS", color = SBg, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.height(30.dp).align(Alignment.CenterHorizontally),
            colorFilter = ColorFilter.tint(SRed)
        )
    }
}
