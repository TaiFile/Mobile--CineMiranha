package br.ufscar.cinemiranha.ui.composable._shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import br.ufscar.cinemiranha.R

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = stringResource(R.string.cd_logo),
        modifier = modifier.wrapContentWidth(unbounded = true),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
    )
}
