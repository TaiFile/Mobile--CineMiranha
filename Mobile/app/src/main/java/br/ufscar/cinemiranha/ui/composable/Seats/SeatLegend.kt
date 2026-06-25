package br.ufscar.cinemiranha.ui.composable.Seats

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun SeatLegend() {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpaceS)) {
        Text(stringResource(R.string.legend), color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        LegendItem(Color.White, stringResource(R.string.legend_best_view))
        LegendItem(MaterialTheme.colorScheme.outline, stringResource(R.string.legend_occupied), isOccupied = true)
        LegendItem(MaterialTheme.colorScheme.primary, stringResource(R.string.legend_selected))
    }
}

@Composable
private fun LegendItem(color: Color, text: String, isOccupied: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color)
                .border(1.dp, MaterialTheme.colorScheme.outline),
            contentAlignment = Alignment.Center
        ) {
            if (isOccupied) Text("X", color = Color.Black, fontSize = 8.sp)
        }
        Spacer(modifier = Modifier.width(Dimens.SpaceS))
        Text(text = text, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodySmall)
    }
}
