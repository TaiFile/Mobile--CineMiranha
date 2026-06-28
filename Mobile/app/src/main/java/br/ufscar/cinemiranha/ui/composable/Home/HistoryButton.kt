package br.ufscar.cinemiranha.ui.composable.Home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.ufscar.cinemiranha.R

@Composable
fun HistoryButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.History,
            contentDescription = stringResource(R.string.purchase_history_title),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}
