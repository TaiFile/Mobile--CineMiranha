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
import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.model.dto.SessionResponse
import br.ufscar.cinemiranha.ui.theme.Dimens

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
        contentPadding = PaddingValues(bottom = Dimens.SpaceL)
    ) {
        item {
            Text(
                text = stringResource(R.string.choose_session),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = Dimens.SpaceL, vertical = 14.dp)
            )
        }

        if (movie != null) {
            item { MovieRow(movie) }
        }

        item { Spacer(modifier = Modifier.height(Dimens.SpaceL)) }

        item {
            DatePicker(
                dates = dates,
                selectedDate = selectedDate,
                sessions = sessions,
                onDateSelected = onDateSelected
            )
        }

        item { Spacer(modifier = Modifier.height(Dimens.SpaceM)) }

        item {
            HorizontalDivider(color = MaterialTheme.colorScheme.outline, modifier = Modifier.padding(horizontal = Dimens.SpaceL))
        }

        item { Spacer(modifier = Modifier.height(Dimens.SpaceM)) }

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

        item { Spacer(modifier = Modifier.height(Dimens.SpaceL)) }

        if (byRoom.isEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.no_sessions),
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = Dimens.SpaceL)
                )
            }
        } else {
            byRoom.forEach { (roomName, roomSessions) ->
                item {
                    RoomSection(roomName = roomName, sessions = roomSessions, onSessionSelected = onSessionSelected)
                    Spacer(modifier = Modifier.height(Dimens.SpaceL))
                }
            }
        }
    }
}
