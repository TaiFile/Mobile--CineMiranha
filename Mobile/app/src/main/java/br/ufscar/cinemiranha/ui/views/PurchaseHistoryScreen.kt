package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.database.PurchaseEntity
import br.ufscar.cinemiranha.ui.composable._shared.BottomBar
import br.ufscar.cinemiranha.ui.composable._shared.LoadingState
import br.ufscar.cinemiranha.ui.composable._shared.TopBar
import br.ufscar.cinemiranha.ui.theme.Dimens
import java.util.Locale

@Composable
fun PurchaseHistoryScreen(
    purchases: List<PurchaseEntity>,
    isLoading: Boolean,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = { TopBar(onBack = onBack) },
        bottomBar = { BottomBar() },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = stringResource(R.string.purchase_history_title),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(Dimens.SpaceL).fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            when {
                isLoading -> LoadingState()
                purchases.isEmpty() -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.purchase_history_empty),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Dimens.SpaceL),
                    verticalArrangement = Arrangement.spacedBy(Dimens.SpaceM)
                ) {
                    items(purchases) { purchase ->
                        PurchaseHistoryItem(purchase = purchase)
                    }
                }
            }
        }
    }
}

@Composable
private fun PurchaseHistoryItem(purchase: PurchaseEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(Dimens.SpaceL)) {
            Text(
                text = purchase.movieTitle,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(Dimens.SpaceS))
            Text(
                text = "${purchase.sessionDate}  ${purchase.sessionTime}",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(R.string.purchase_history_seats, purchase.seats),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(Dimens.SpaceS))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val labelFull = stringResource(R.string.ticket_full)
                val labelHalf = stringResource(R.string.ticket_half)
                val ticketDesc = buildString {
                    if (purchase.fullTickets > 0) append("${purchase.fullTickets}x $labelFull")
                    if (purchase.fullTickets > 0 && purchase.halfTickets > 0) append(", ")
                    if (purchase.halfTickets > 0) append("${purchase.halfTickets}x $labelHalf")
                }
                Text(text = ticketDesc, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodySmall)
                Text(
                    text = "R$ ${String.format(Locale.getDefault(), "%.2f", purchase.totalPrice)}",
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
