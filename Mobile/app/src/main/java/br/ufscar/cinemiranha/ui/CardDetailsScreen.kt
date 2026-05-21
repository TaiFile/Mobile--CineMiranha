package br.ufscar.cinemiranha.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.ui.components.Stepper

private val SBg = Color(0xFF1F2024)
private val SSurface = Color(0xFF2F3036)
private val SRed = Color(0xFFBF0903)
private val SPrimary = Color(0xFFFAFAFA)
private val SSecond = Color(0xFF8F9098)
private val SDivider = Color(0xFF494A50)

@Composable
fun CardDetailsScreen(
    ticketTotal: Float,
    snackTotal: Float,
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Scaffold(
        topBar = { CardTopBar(onBack = onBack) },
        bottomBar = { CardBottomBar() },
        containerColor = SBg
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Stepper(currentStep = 6)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Coloque os dados do cartão",
                        color = SPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        CardTextField(value = name, onValueChange = { name = it }, label = "Nome do Titular")
                        CardTextField(value = cpf, onValueChange = { cpf = it }, label = "CPF do Titular")
                        CardTextField(value = cardNumber, onValueChange = { cardNumber = it }, label = "Número do Cartão")
                        CardTextField(value = expiry, onValueChange = { expiry = it }, label = "Data de Validade")
                        CardTextField(value = cvv, onValueChange = { cvv = it }, label = "Código de Segurança")
                    }
                }

                item {
                    OrderTotalCard(ticketTotal = ticketTotal, snackTotal = snackTotal)
                }

                item {
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SSecond),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("CONFIRMAR COMPRA", color = SBg, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun CardTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text(label, color = SSecond) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = SSurface,
            unfocusedContainerColor = SSurface,
            disabledContainerColor = SSurface,
            cursorColor = SPrimary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = SPrimary,
            unfocusedTextColor = SPrimary
        ),
        singleLine = true
    )
}

@Composable
private fun CardTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SSurface)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = SPrimary)
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.height(36.dp),
            colorFilter = ColorFilter.tint(SRed)
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
private fun CardBottomBar() {
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
            contentDescription = "Logo",
            modifier = Modifier.height(30.dp),
            colorFilter = ColorFilter.tint(SRed)
        )
    }
}
