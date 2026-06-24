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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.ufscar.cinemiranha.model.SessionResponse
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun SessionTimeCard(session: SessionResponse, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = Dimens.SpaceS),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = session.timeLabel(),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(Dimens.SpaceXS))
        Row(horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceXS)) {
            FormatBadge(session.formatLabel())
            SubtitleBadge(session.subtitleLabel())
        }
    }
}

@Composable
fun FormatBadge(label: String) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .background(color = MaterialTheme.colorScheme.outline)
            .padding(horizontal = 5.dp, vertical = 2.dp)
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun SubtitleBadge(label: String) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .background(MaterialTheme.colorScheme.outline)
            .padding(horizontal = 5.dp, vertical = 2.dp)
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.labelSmall)
    }
}
