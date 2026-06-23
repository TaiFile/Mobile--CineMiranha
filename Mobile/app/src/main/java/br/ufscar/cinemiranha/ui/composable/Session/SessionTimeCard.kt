package br.ufscar.cinemiranha.ui.composable.Session

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.model.SessionResponse
import br.ufscar.cinemiranha.ui.screens.FormatBadge
import br.ufscar.cinemiranha.ui.screens.SBg
import br.ufscar.cinemiranha.ui.screens.SDivider
import br.ufscar.cinemiranha.ui.screens.SPrimary
import br.ufscar.cinemiranha.ui.screens.SubtitleBadge

@Composable
private fun SessionTimeCard(session: SessionResponse, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = session.timeLabel(),
            color = SBg,
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
            .background(SDivider)
            .padding(horizontal = 5.dp, vertical = 2.dp)
    ) {
        Text(text = label, color = SPrimary, fontSize = 10.sp)
    }
}

@Composable
private fun SubtitleBadge(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .background(SDivider)
            .padding(horizontal = 5.dp, vertical = 2.dp)
    ) {
        Text(text = label, color = SPrimary, fontSize = 10.sp)
    }
}