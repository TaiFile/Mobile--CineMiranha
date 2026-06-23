package br.ufscar.cinemiranha.ui.composable.Session

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.model.SessionResponse
@Composable
fun SessionsContent(
    movie: MovieResponse?,
    sessions: List<SessionResponse>,
    selectedDate: String?,
    selectedSubtitle: String?,
    selectedFormat: String?,
    onDateSelected: (String?) -> Unit,
    onSubtitleSelected: (String?) -> Unit,
    onFormatSelected: (String?) -> Unit,
    onSessionSelected: (Long) -> Unit
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
                text = stringResource(R.string.choose_session),
                color = MaterialTheme.colorScheme.onPrimary,
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
            HorizontalDivider(color = MaterialTheme.colorScheme.outline, modifier = Modifier.padding(horizontal = 16.dp))
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
                    text = stringResource(R.string.no_sessions),
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        } else {
            byRoom.forEach { (roomName, roomSessions) ->
                item {
                    RoomSection(roomName = roomName, sessions = roomSessions, onSessionSelected = onSessionSelected)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}