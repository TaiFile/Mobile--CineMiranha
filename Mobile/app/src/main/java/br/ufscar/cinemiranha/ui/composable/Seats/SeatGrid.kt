package br.ufscar.cinemiranha.ui.composable.Seats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufscar.cinemiranha.ui.theme.Dimens

@Composable
fun SeatGrid(selectedSeats: Set<String>, onSeatToggle: (String) -> Unit) {
    val rows = listOf("A", "B", "C", "D", "E")
    val cols = 8

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .padding(Dimens.SpaceL),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceXS)) {
                for (col in 1..cols) {
                    val seatId = "$row$col"
                    val isOccupied = (row == "B" && col == 4) || (row == "C" && col > 5)
                    val isSpecial = row == "C" && col == 5
                    val isSelected = selectedSeats.contains(seatId)

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                when {
                                    isSelected -> MaterialTheme.colorScheme.primary
                                    isOccupied -> MaterialTheme.colorScheme.outline
                                    isSpecial -> Color.White
                                    else -> MaterialTheme.colorScheme.secondary
                                }
                            )
                            .clickable(!isOccupied) { onSeatToggle(seatId) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isOccupied) {
                            Text("X", color = Color.Black, fontSize = 10.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(Dimens.SpaceXS))
        }

        Spacer(modifier = Modifier.height(Dimens.SpaceL))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(4.dp)
                .background(MaterialTheme.colorScheme.outline)
        )
    }
}
