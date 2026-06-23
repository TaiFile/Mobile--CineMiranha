package br.ufscar.cinemiranha.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TicketsUiState(
    val fullPriceCount: Int = 0,
    val halfPriceCount: Int = 0
)

class TicketsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TicketsUiState())
    val uiState: StateFlow<TicketsUiState> = _uiState.asStateFlow()

    fun setTicketCounts(full: Int, half: Int) {
        _uiState.update { it.copy(fullPriceCount = full, halfPriceCount = half) }
    }

    fun getTotalPrice(): Float {
        return (_uiState.value.fullPriceCount * 40f) + (_uiState.value.halfPriceCount * 20f)
    }

    fun getTotalCount(): Int {
        return _uiState.value.fullPriceCount + _uiState.value.halfPriceCount
    }

    fun reset() {
        _uiState.value = TicketsUiState()
    }
}
