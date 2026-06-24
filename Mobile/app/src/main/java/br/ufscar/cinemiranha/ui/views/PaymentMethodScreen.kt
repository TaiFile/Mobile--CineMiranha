package br.ufscar.cinemiranha.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Pix
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.ui.composable.Stepper
import br.ufscar.cinemiranha.ui.theme.Dimens
import br.ufscar.cinemiranha.viewmodel.CheckoutViewModel

@Composable
fun PaymentMethodScreen(
    checkoutViewModel: CheckoutViewModel,
    onBack: () -> Unit,
    onSelectMethod: (String) -> Unit
) {
    val uiState by checkoutViewModel.uiState.collectAsState()

    Scaffold(
        topBar = { PaymentTopBar(onBack = onBack) },
        bottomBar = { PaymentBottomBar() },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Stepper(currentStep = 5)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Dimens.SpaceL),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpaceL)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.choose_payment_method),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    OrderTotalCard(
                        ticketCount = uiState.fullPriceCount + uiState.halfPriceCount,
                        ticketTotal = checkoutViewModel.getTotalTicketPrice(),
                        snackTotal = checkoutViewModel.getTotalSnackPrice()
                    )
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpaceM)) {
                        PaymentMethodButton(stringResource(R.string.payment_credit_card), Icons.Default.CreditCard) { onSelectMethod("CREDIT") }
                        PaymentMethodButton(stringResource(R.string.payment_debit_card), Icons.Default.CreditCard) { onSelectMethod("DEBIT") }
                        PaymentMethodButton(stringResource(R.string.payment_pix), Icons.Default.Pix) { onSelectMethod("PIX") }
                    }
                }
            }
        }
    }
}

@Composable
private fun PaymentMethodButton(label: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(horizontal = Dimens.SpaceL)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.width(Dimens.SpaceL))
            Text(text = label, color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun PaymentTopBar(onBack: () -> Unit) {
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
private fun PaymentBottomBar() {
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
