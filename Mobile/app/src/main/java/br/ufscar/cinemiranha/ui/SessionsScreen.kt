package br.ufscar.cinemiranha.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.model.SessionResponse
import br.ufscar.cinemiranha.viewmodel.SessionsViewModel
import coil.compose.AsyncImage

private val SBg      = Color(0xFF1F2024)
private val SSurface = Color(0xFF2F3036)
private val SRed     = Color(0xFFBF0903)
private val SPrimary = Color(0xFFFAFAFA)
private val SSecond  = Color(0xFF8F9098)
private val SDivider = Color(0xFF494A50)

@Composable
fun SessionsScreen(movieId: Long, onBack: () -> Unit) {
    val vm: SessionsViewModel = viewModel(factory = SessionsViewModel.factory(movieId))
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar    = { SessionsTopBar(onBack = onBack) },
        bottomBar = { SessionsBottomBar() },
        containerColor = SBg
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator(color = SRed)
                }
                state.errorMessage != null -> SessionsError(
                    message = state.errorMessage!!,
                    onRetry = { vm.load() }
                )
                else -> SessionsContent(
                    movie           = state.movie,
                    sessions        = state.sessions,
                    selectedDate    = state.selectedDate,
                    selectedSubtitle = state.selectedSubtitle,
                    selectedFormat  = state.selectedFormat,
                    onDateSelected  = { vm.selectDate(it) },
                    onSubtitleSelected = { vm.selectSubtitle(it) },
                    onFormatSelected   = { vm.selectFormat(it) }
                )
            }
        }
    }
}

@Composable
private fun SessionsTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SBg)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = SPrimary
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(36.dp)
                .wrapContentWidth(unbounded = true),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
private fun SessionsBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SBg)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(50.dp)
                .wrapContentWidth(unbounded = true),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun SessionsContent(
    movie: MovieResponse?,
    sessions: List<SessionResponse>,
    selectedDate: String?,
    selectedSubtitle: String?,
    selectedFormat: String?,
    onDateSelected: (String?) -> Unit,
    onSubtitleSelected: (String?) -> Unit,
    onFormatSelected: (String?) -> Unit
) {
    val dates = sessions.map { it.dateDayLabel() }.distinct().sorted()

    val filtered = sessions.filter { s ->
        (selectedDate == null || s.dateDayLabel() == selectedDate) &&
        (selectedSubtitle == null || s.subtitleLabel() == selectedSubtitle) &&
        (selectedFormat == null || s.formatLabel() == selectedFormat)
    }

    val byRoom = filtered
        .groupBy { it.roomName ?: "Sala ${it.roomId}" }
        .toSortedMap()

    val subtitleOptions = sessions.map { it.subtitleLabel() }.distinct().sorted()
    val formatOptions   = sessions.map { it.formatLabel() }.distinct().sorted()

    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Text(
                text = "Escolha da sessão",
                color = SPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
            )
        }

        if (movie != null) {
            item { MovieRow(movie) }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            DatePicker(
                dates = dates,
                selectedDate = selectedDate,
                sessions = sessions,
                onDateSelected = onDateSelected
            )
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }

        item {
            HorizontalDivider(color = SDivider, modifier = Modifier.padding(horizontal = 16.dp))
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }

        item {
            FilterRow(
                subtitleOptions  = subtitleOptions,
                formatOptions    = formatOptions,
                selectedSubtitle = selectedSubtitle,
                selectedFormat   = selectedFormat,
                onSubtitleSelected = onSubtitleSelected,
                onFormatSelected   = onFormatSelected
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        if (byRoom.isEmpty()) {
            item {
                Text(
                    text = "Nenhuma sessão disponível",
                    color = SSecond,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        } else {
            byRoom.forEach { (roomName, roomSessions) ->
                item {
                    RoomSection(roomName = roomName, sessions = roomSessions)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun MovieRow(movie: MovieResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
        Text(
            text = movie.title.uppercase(),
            color = SPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 20.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun DatePicker(
    dates: List<String>,
    selectedDate: String?,
    sessions: List<SessionResponse>,
    onDateSelected: (String?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dates) { date ->
            val weekDay = sessions.firstOrNull { it.dateDayLabel() == date }?.weekDayLabel() ?: ""
            val selected = date == selectedDate
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (selected) SRed else SSurface)
                    .clickable { onDateSelected(date) }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = weekDay,
                    color = if (selected) SPrimary else SSecond,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date,
                    color = if (selected) SPrimary else SPrimary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun FilterRow(
    subtitleOptions: List<String>,
    formatOptions: List<String>,
    selectedSubtitle: String?,
    selectedFormat: String?,
    onSubtitleSelected: (String?) -> Unit,
    onFormatSelected: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FilterDropdown(
            label = selectedSubtitle ?: "Legenda",
            options = listOf(null) + subtitleOptions.map { it as String? },
            onSelected = onSubtitleSelected
        )
        FilterDropdown(
            label = selectedFormat ?: "Formato",
            options = listOf(null) + formatOptions.map { it as String? },
            onSelected = onFormatSelected
        )
    }
}

@Composable
private fun FilterDropdown(
    label: String,
    options: List<String?>,
    onSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(SSurface)
                .clickable { expanded = true }
                .padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, color = SPrimary, fontSize = 13.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = SSecond,
                modifier = Modifier.size(16.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = SSurface
        ) {
            options.forEach { opt ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = opt ?: "Todos",
                            color = SPrimary,
                            fontSize = 13.sp
                        )
                    },
                    onClick = {
                        onSelected(opt)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun RoomSection(roomName: String, sessions: List<SessionResponse>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(SRed)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(text = roomName, color = SPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        val columns = 3
        val rows = (sessions.size + columns - 1) / columns

        repeat(rows) { rowIdx ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(columns) { colIdx ->
                    val idx = rowIdx * columns + colIdx
                    if (idx < sessions.size) {
                        SessionTimeCard(sessions[idx], modifier = Modifier.weight(1f))
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            if (rowIdx < rows - 1) Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SessionTimeCard(session: SessionResponse, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .border(1.dp, SDivider, RoundedCornerShape(6.dp))
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = session.timeLabel(),
            color = SPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            FormatBadge(session.formatLabel())
            SubtitleBadge(session.subtitleLabel())
        }
    }
}

@Composable
private fun FormatBadge(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .background(SSurface)
            .padding(horizontal = 5.dp, vertical = 2.dp)
    ) {
        Text(text = label, color = SSecond, fontSize = 10.sp)
    }
}

@Composable
private fun SubtitleBadge(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .background(SSurface)
            .padding(horizontal = 5.dp, vertical = 2.dp)
    ) {
        Text(text = label, color = SSecond, fontSize = 10.sp)
    }
}

@Composable
private fun SessionsError(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = SSecond,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onRetry) {
            Icon(Icons.Default.Refresh, contentDescription = null, tint = SRed, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Tentar novamente", color = SRed)
        }
    }
}
