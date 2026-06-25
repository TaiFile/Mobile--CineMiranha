package br.ufscar.cinemiranha.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class SeatsUiState(
    val selectedSeats: List<String> = emptyList()
)

class SeatsViewModel : ViewModel() {
    var uiState by mutableStateOf(SeatsUiState())
        private set

    fun toggleSeat(seat: String) {
        val currentSeats = uiState.selectedSeats
        val newSeats = if (currentSeats.contains(seat)) {
            currentSeats - seat
        } else {
            currentSeats + seat
        }
        uiState = uiState.copy(selectedSeats = newSeats)
    }

    fun reset() {
        uiState = SeatsUiState()
    }
}
