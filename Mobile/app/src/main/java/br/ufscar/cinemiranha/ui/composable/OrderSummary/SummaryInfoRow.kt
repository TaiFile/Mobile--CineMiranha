package br.ufscar.cinemiranha.ui.composable.OrderSummary

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun SummaryInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.width(100.dp))
        Spacer(modifier = Modifier.width(Dimens.SpaceS))
        Text(text = value, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
    }
}
