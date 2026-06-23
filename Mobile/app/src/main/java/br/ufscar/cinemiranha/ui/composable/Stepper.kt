package br.ufscar.cinemiranha.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val SRed = Color(0xFFBF0903)
private val SDivider = Color(0xFF494A50)
private val SSecond = Color(0xFF8F9098)

@Composable
fun Stepper(currentStep: Int, totalSteps: Int = 8) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until totalSteps) {
            val isActive = i == currentStep
            
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(if (isActive) SRed else Color.White)
            )
            
            if (i < totalSteps - 1) {
                Box(
                    modifier = Modifier
                        .size(width = 12.dp, height = 2.dp)
                        .background(SDivider)
                )
            }
        }
    }
}
