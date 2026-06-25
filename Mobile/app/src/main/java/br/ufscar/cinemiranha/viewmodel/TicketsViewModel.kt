package br.ufscar.cinemiranha.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class TicketsUiState(
    val fullPriceCount: Int = 0,
    val halfPriceCount: Int = 0
)

class TicketsViewModel : ViewModel() {
    var uiState by mutableStateOf(TicketsUiState())
        private set

    fun setTicketCounts(full: Int, half: Int) {
        uiState = uiState.copy(fullPriceCount = full, halfPriceCount = half)
    }

    fun getTotalCount(): Int {
        return uiState.fullPriceCount + uiState.halfPriceCount
    }

    fun getTotalPrice(fullPrice: Float, halfPrice: Float): Float {
        return (uiState.fullPriceCount * fullPrice) + (uiState.halfPriceCount * halfPrice)
    }

    fun reset() {
        uiState = TicketsUiState()
    }
}
