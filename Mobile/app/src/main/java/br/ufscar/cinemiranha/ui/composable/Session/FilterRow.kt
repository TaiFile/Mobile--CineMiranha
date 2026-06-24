package br.ufscar.cinemiranha.ui.composable.Session

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun FilterRow(
    subtitleOptions: List<String>,
    selectedSubtitle: String?,
    onSubtitleSelected: (String?) -> Unit
) {
    if (subtitleOptions.size > 1) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = Dimens.SpaceL),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Dimens.SpaceS)
        ) {
            items(subtitleOptions) { option ->
                val isSelected = option == selectedSubtitle
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surface
                        )
                        .clickable { onSubtitleSelected(
                            if (isSelected)
                                null
                            else
                                option
                        )}
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
}
