package br.ufscar.cinemiranha.ui.composable.Session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.ufscar.cinemiranha.R

@Composable
fun FilterRow(
    subtitleOptions: List<String>,
    formatOptions: List<String>,
    selectedSubtitle: String?,
    selectedFormat: String?,
    onSubtitleSelected: (String?) -> Unit,
    onFormatSelected: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FilterDropdown(
            label = selectedSubtitle ?: stringResource(R.string.filter_subtitle),
            options = listOf(null) + subtitleOptions.map { it as String? },
            onSelected = onSubtitleSelected
        )
        FilterDropdown(
            label = selectedFormat ?: stringResource(R.string.filter_format),
            options = listOf(null) + formatOptions.map { it as String? },
            onSelected = onFormatSelected
        )
    }
}