package br.ufscar.cinemiranha.ui

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
fun OrderSummaryScreen(
    movieId: Long,
    sessionId: Long,
    selectedSeats: List<String>,
    fullPriceCount: Int,
    halfPriceCount: Int,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val vm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
    val state by vm.uiState.collectAsState()
    val session = state.sessions.find { it.id == sessionId }

    Scaffold(
        topBar = { SummaryTopBar(onBack = onBack) },
        bottomBar = { SummaryBottomBar() },
        containerColor = SBg
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Stepper(currentStep = 4)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Confirma seu pedido",
                        color = SPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                if (state.movie != null && session != null) {
                    item {
                        SummaryMovieInfo(state.movie!!, session)
                    }

                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            HorizontalDivider(color = SDivider)
                            SummaryInfoRow("Local:", "Unidade UFSCAR")
                            HorizontalDivider(color = SDivider)
                            SummaryInfoRow("Data e hora:", "${session.dateDayLabel()}  ${session.timeLabel()}")
                            HorizontalDivider(color = SDivider)
                            SummaryInfoRow("Assentos:", "${selectedSeats.joinToString(", ")} (Meia - R$ 20,00)") // simplified
                            HorizontalDivider(color = SDivider)
                            SummaryInfoRow("Snackbar:", "---")
                            HorizontalDivider(color = SDivider)
                        }
                    }
                }

                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Editar compra", color = SBg)
                        }
                    }
                }

                item {
                    OrderTotalCard(
                        ticketTotal = (fullPriceCount * 40 + halfPriceCount * 20).toFloat(),
                        snackTotal = 0f
                    )
                }

                item {
                    Button(
                        onClick = onNext,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SSecond),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("AVANÇAR", color = SBg, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderTotalCard(ticketTotal: Float, snackTotal: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text("Seu Pedido:", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.Black.copy(alpha = 0.1f))
        
        SummaryPriceRow("1 Ingressos", "R$ ${String.format("%.2f", ticketTotal)}")
        SummaryPriceRow("Snackbar", "R$ ${String.format("%.2f", snackTotal)}")
        SummaryPriceRow("Desconto", "R$ 00,00")
        
        HorizontalDivider(color = Color.Black.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 8.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("TOTAL", color = Color.Black, fontWeight = FontWeight.Bold)
            Text("R$ ${String.format("%.2f", ticketTotal + snackTotal)}", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SummaryPriceRow(label: String, price: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = SSecond, fontSize = 14.sp)
        Text(text = price, color = Color.Black, fontSize = 14.sp)
    }
}

@Composable
private fun SummaryInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, color = SPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, color = SSecond, fontSize = 14.sp)
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
                .background(SSurface),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
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
                text = "${session.formatLabel()}  ${session.subtitleLabel()}",
                color = SSecond,
                fontSize = 12.sp
            )
        }
        Icon(Icons.Default.Delete, contentDescription = "Remover", tint = SSecond)
    }
}

@Composable
private fun SummaryTopBar(onBack: () -> Unit) {
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
private fun SummaryBottomBar() {
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
            contentDescription = "Logo",
            modifier = Modifier.height(30.dp),
            colorFilter = ColorFilter.tint(SRed)
        )
    }
}
