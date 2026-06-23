package br.ufscar.cinemiranha.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SeatsUiState(
    val selectedSeats: List<String> = emptyList()
)

class SeatsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SeatsUiState())
    val uiState: StateFlow<SeatsUiState> = _uiState.asStateFlow()

    fun setSelectedSeats(seats: List<String>) {
        _uiState.update { it.copy(selectedSeats = seats) }
    }

    fun reset() {
        _uiState.value = SeatsUiState()
    }
}
