package br.ufscar.cinemiranha.ui.composable.Session

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.model.SessionResponse
import br.ufscar.cinemiranha.ui.screens.SPrimary
import br.ufscar.cinemiranha.ui.screens.SRed

@Composable
private fun RoomSection(roomName: String, sessions: List<SessionResponse>, onSessionSelected: (Long) -> Unit) {
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
                        SessionTimeCard(
                            session = sessions[idx],
                            modifier = Modifier.weight(1f),
                            onClick = { onSessionSelected(sessions[idx].id) }
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            if (rowIdx < rows - 1) Spacer(modifier = Modifier.height(8.dp))
        }
    }
}