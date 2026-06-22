package br.ufscar.cinemiranha.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.ui.components.Stepper
import br.ufscar.cinemiranha.viewmodel.CheckoutViewModel

private val SBg = Color(0xFF1F2024)
private val SSurface = Color(0xFF2F3036)
private val SRed = Color(0xFFBF0903)
private val SPrimary = Color(0xFFFAFAFA)

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
        containerColor = SBg
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Stepper(currentStep = 5)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.choose_payment_method),
                        color = SPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
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
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
        colors = ButtonDefaults.buttonColors(containerColor = SSurface),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = SPrimary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, color = SPrimary, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun PaymentTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SSurface)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.cd_back), tint = SPrimary)
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.cd_logo),
            modifier = Modifier.height(36.dp),
            colorFilter = ColorFilter.tint(SRed)
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
private fun PaymentBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SSurface)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.cd_logo),
            modifier = Modifier.height(30.dp),
            colorFilter = ColorFilter.tint(SRed)
        )
    }
}
