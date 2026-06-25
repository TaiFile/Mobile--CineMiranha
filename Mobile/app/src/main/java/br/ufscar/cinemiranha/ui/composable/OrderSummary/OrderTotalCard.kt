package br.ufscar.cinemiranha.ui.composable.OrderSummary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.ui.theme.Dimens
import java.util.Locale

@Composable
fun OrderTotalCard(ticketCount: Int, ticketTotal: Float, snackTotal: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
            .padding(Dimens.SpaceL)
    ) {
        Text(stringResource(R.string.order_summary_title), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(Dimens.SpaceS))
        HorizontalDivider(color = Color.Black.copy(alpha = 0.1f))

        SummaryPriceRow(stringResource(R.string.summary_tickets_count, ticketCount), "R$ ${String.format(Locale.getDefault(), "%.2f", ticketTotal)}")
        SummaryPriceRow(stringResource(R.string.summary_snackbar_label), "R$ ${String.format(Locale.getDefault(), "%.2f", snackTotal)}")
        SummaryPriceRow(stringResource(R.string.summary_discount), "R$ 0,00")

        HorizontalDivider(color = Color.Black.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = Dimens.SpaceS))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.summary_total), color = Color.Black, fontWeight = FontWeight.Bold)
            Text("R$ ${String.format(Locale.getDefault(), "%.2f", ticketTotal + snackTotal)}", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SummaryPriceRow(label: String, price: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.SpaceS),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodyLarge)
        Text(text = price, color = Color.Black, style = MaterialTheme.typography.bodyLarge)
    }
}
