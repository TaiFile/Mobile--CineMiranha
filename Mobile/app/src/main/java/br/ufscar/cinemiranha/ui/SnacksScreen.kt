package br.ufscar.cinemiranha.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun SnacksScreen(onBack: () -> Unit, onNext: () -> Unit) {
    val categories = listOf("Pipoca", "Doces", "Bebidas", "Combos", "Combos de filmes", "Promoções")
    var selectedCategory by remember { mutableStateOf("Pipoca") }

    Scaffold(
        topBar = { SnacksTopBar(onBack = onBack) },
        bottomBar = { SnacksBottomBar() },
        containerColor = SBg
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Stepper(currentStep = 3)

            Text(
                text = "Escolha seus snacks",
                color = SPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = SSurface,
                            labelColor = SSecond,
                            selectedContainerColor = SDivider,
                            selectedLabelColor = SPrimary
                        ),
                        border = null
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    SnackItem(
                        name = "Pipoca Salgada P",
                        desc = "250g de pipoca salgada",
                        price = "R$19,99",
                        imageRes = R.drawable.logo // Placeholder
                    )
                }
                item {
                    SnackItem(
                        name = "Pipoca Salgada M",
                        desc = "400g de pipoca salgada",
                        price = "R$29,99",
                        imageRes = R.drawable.logo // Placeholder
                    )
                }
                item {
                    SnackItem(
                        name = "Balde de pipoca salgada",
                        desc = "500g de pipoca salgada",
                        price = "R$59,99",
                        imageRes = R.drawable.logo // Placeholder
                    )
                }
            }

            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SSecond),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("AVANÇAR", color = SBg, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun SnackItem(name: String, desc: String, price: String, imageRes: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SSurface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Text(text = name, color = SPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = desc, color = SSecond, fontSize = 12.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = price, color = SPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {}, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.RemoveCircleOutline, null, tint = SPrimary)
                }
                Text(text = "0", color = SPrimary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp))
                IconButton(onClick = {}, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.AddCircleOutline, null, tint = SPrimary)
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
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(SRed) // Using logo as placeholder
            )
        }
    }
}

@Composable
private fun SnacksTopBar(onBack: () -> Unit) {
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
private fun SnacksBottomBar() {
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
