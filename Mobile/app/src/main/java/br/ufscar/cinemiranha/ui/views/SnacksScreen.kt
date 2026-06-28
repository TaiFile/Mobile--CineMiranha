package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.Snack
import br.ufscar.cinemiranha.ui.composable.Snacks.SnackItem
import br.ufscar.cinemiranha.ui.composable._shared.BottomBar
import br.ufscar.cinemiranha.ui.composable._shared.ErrorState
import br.ufscar.cinemiranha.ui.composable._shared.LoadingState
import br.ufscar.cinemiranha.ui.composable._shared.Stepper
import br.ufscar.cinemiranha.ui.composable._shared.TopBar
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun SnacksScreen(
    isLoading: Boolean,
    errorMessage: String?,
    availableSnacks: List<Snack>,
    selectedSnacks: Map<Int, Int>,
    onUpdateSnackQuantity: (Int, Int) -> Unit,
    onRetry: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
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
            Stepper(currentStep = 3)

            Text(
                text = stringResource(R.string.choose_snacks),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(Dimens.SpaceL).fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Box(modifier = Modifier.weight(1f)) {
                when {
                    isLoading -> LoadingState()
                    errorMessage != null -> ErrorState(message = errorMessage, onRetry = onRetry)
                    else -> LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(Dimens.SpaceL),
                        verticalArrangement = Arrangement.spacedBy(Dimens.SpaceL)
                    ) {
                        items(availableSnacks) { snack ->
                            SnackItem(
                                snack = snack,
                                quantity = selectedSnacks.getOrDefault(snack.id, 0),
                                onAdd = { onUpdateSnackQuantity(snack.id, 1) },
                                onRemove = { onUpdateSnackQuantity(snack.id, -1) }
                            )
                        }
                    }
                }
            }

            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SpaceL)
                    .height(Dimens.ButtonHeight),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(stringResource(R.string.btn_next), color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold)
            }
        }
    }
}
