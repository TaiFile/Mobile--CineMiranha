package br.ufscar.cinemiranha.ui.composable.Session

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.model.dto.SessionResponse
import br.ufscar.cinemiranha.ui.theme.Dimens


@Composable
fun DatePicker(
    dates: List<String>,
    selectedDate: String?,
    sessions: List<SessionResponse>,
    onDateSelected: (String?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Dimens.SpaceL),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceS)
    ) {
        items(dates) { date ->
            val weekDay = sessions.firstOrNull { it.dateDayLabel() == date }?.weekDayLabel() ?: ""
            val selected = date == selectedDate
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(
                        if (selected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surface)
                    .clickable { onDateSelected(
                        if (selected)
                            null
                        else
                            date)
                    }
                    .padding(horizontal = Dimens.SpaceM, vertical = Dimens.SpaceS),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = weekDay,
                    color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
