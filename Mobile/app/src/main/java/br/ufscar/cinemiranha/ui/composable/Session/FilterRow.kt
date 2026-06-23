package br.ufscar.cinemiranha.ui.composable.Session

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun FilterRow(
    subtitleOptions: List<String>,
    formatOptions: List<String>,
    selectedSubtitle: String?,
    selectedFormat: String?,
    onSubtitleSelected: (String?) -> Unit,
    onFormatSelected: (String?) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpaceS)
    ) {
        if (subtitleOptions.size > 1) {
            FilterChipRow(
                options = subtitleOptions,
                selected = selectedSubtitle,
                onSelected = onSubtitleSelected
            )
        }
        if (formatOptions.size > 1) {
            FilterChipRow(
                options = formatOptions,
                selected = selectedFormat,
                onSelected = onFormatSelected
            )
        }
    }
}

@Composable
private fun FilterChipRow(
    options: List<String>,
    selected: String?,
    onSelected: (String?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Dimens.SpaceL),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceS)
    ) {
        items(options) { option ->
            val isSelected = option == selected
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surface
                    )
                    .clickable { onSelected(if (isSelected) null else option) }
                    .padding(horizontal = Dimens.SpaceM, vertical = Dimens.SpaceS)
            ) {
                Text(
                    text = option,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
