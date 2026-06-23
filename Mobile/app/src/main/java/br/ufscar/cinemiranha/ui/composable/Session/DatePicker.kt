package br.ufscar.cinemiranha.ui.composable.Session

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.model.SessionResponse
import br.ufscar.cinemiranha.ui.screens.SPrimary
import br.ufscar.cinemiranha.ui.screens.SRed
import br.ufscar.cinemiranha.ui.screens.SSecond
import br.ufscar.cinemiranha.ui.screens.SSurface


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
