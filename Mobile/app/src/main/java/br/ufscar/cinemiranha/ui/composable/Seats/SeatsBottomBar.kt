package br.ufscar.cinemiranha.ui.composable.Seats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun SeatsBottomBar(selectedCount: Int, onConfirm: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(Dimens.SpaceL)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.outline),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "$selectedCount", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onConfirm,
                enabled = selectedCount > 0,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, disabledContainerColor = MaterialTheme.colorScheme.outline),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .height(Dimens.ButtonHeight)
                    .fillMaxWidth(0.8f)
            ) {
                Text(stringResource(R.string.btn_choose_tickets), color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(Dimens.SpaceS))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.cd_logo),
            modifier = Modifier.height(30.dp).align(Alignment.CenterHorizontally),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}
