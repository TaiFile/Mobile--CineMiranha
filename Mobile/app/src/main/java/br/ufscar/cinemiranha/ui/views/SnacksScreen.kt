package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.ui.composable._shared.Stepper
import br.ufscar.cinemiranha.ui.theme.Dimens
import br.ufscar.cinemiranha.viewmodel.CheckoutViewModel
import br.ufscar.cinemiranha.viewmodel.Snack
import java.util.Locale

@Composable
fun SnacksScreen(
    viewModel: CheckoutViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val categories = listOf(
        stringResource(R.string.cat_popcorn),
        stringResource(R.string.cat_sweets),
        stringResource(R.string.cat_drinks),
        stringResource(R.string.cat_combos),
        stringResource(R.string.cat_movie_combos),
        stringResource(R.string.cat_promotions)
    )
    var selectedCategory by remember { mutableStateOf("") }
    if (selectedCategory.isEmpty()) {
        selectedCategory = categories[0]
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { SnacksTopBar(onBack = onBack) },
        bottomBar = { SnacksBottomBar() },
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

            LazyRow(
                contentPadding = PaddingValues(horizontal = Dimens.SpaceL),
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceS),
                modifier = Modifier.padding(bottom = Dimens.SpaceL)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            labelColor = MaterialTheme.colorScheme.secondary,
                            selectedContainerColor = MaterialTheme.colorScheme.outline,
                            selectedLabelColor = MaterialTheme.colorScheme.onBackground
                        ),
                        border = null
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(Dimens.SpaceL),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpaceL)
            ) {
                items(viewModel.availableSnacks) { snack ->
                    SnackItem(
                        snack = snack,
                        quantity = uiState.selectedSnacks.getOrDefault(snack.id, 0),
                        onAdd = { viewModel.updateSnackQuantity(snack.id, 1) },
                        onRemove = { viewModel.updateSnackQuantity(snack.id, -1) }
                    )
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

@Composable
private fun SnackItem(
    snack: Snack,
    quantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(Dimens.SpaceL)
        ) {
            Text(text = snack.name, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = snack.description, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "R$ ${String.format(Locale.getDefault(), "%.2f", snack.price)}",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.RemoveCircleOutline, stringResource(R.string.cd_remove), tint = MaterialTheme.colorScheme.onBackground)
                }
                Text(text = "$quantity", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = Dimens.SpaceS))
                IconButton(onClick = onAdd, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.AddCircleOutline, null, tint = MaterialTheme.colorScheme.onBackground)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(120.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = snack.imageRes),
                contentDescription = snack.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
private fun SnacksTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = Dimens.SpaceS, vertical = Dimens.SpaceM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.cd_back), tint = MaterialTheme.colorScheme.onBackground)
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.cd_logo),
            modifier = Modifier.height(36.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(Dimens.ButtonHeight))
    }
}

@Composable
private fun SnacksBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.cd_logo),
            modifier = Modifier.height(30.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}
