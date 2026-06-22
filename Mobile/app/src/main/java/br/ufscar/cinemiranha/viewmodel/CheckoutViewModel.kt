package br.ufscar.cinemiranha.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import br.ufscar.cinemiranha.R

data class Snack(
    val id: Int,
    val name: String,
    val description: String,
    val price: Float,
    val imageRes: Int
)

data class CheckoutUiState(
    val selectedSeats: List<String> = emptyList(),
    val fullPriceCount: Int = 0,
    val halfPriceCount: Int = 0,
    val selectedSnacks: Map<Int, Int> = emptyMap() // snackId -> quantity
)

class CheckoutViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    val availableSnacks = listOf(
        Snack(1, "Pipoca Salgada P", "250g de pipoca salgada", 19.99f, R.drawable.logo),
        Snack(2, "Pipoca Salgada M", "400g de pipoca salgada", 29.99f, R.drawable.logo),
        Snack(3, "Balde de pipoca salgada", "500g de pipoca salgada", 59.99f, R.drawable.logo),
        Snack(4, "Refrigerante 500ml", "Coca-cola, Fanta ou Sprite", 12.00f, R.drawable.logo),
        Snack(5, "Água Mineral 500ml", "Com ou sem gás", 6.00f, R.drawable.logo)
    )

    fun resetCheckout() {
        _uiState.value = CheckoutUiState()
    }

    fun setSelectedSeats(seats: List<String>) {
        _uiState.update { it.copy(selectedSeats = seats) }
    }

    fun setTicketCounts(full: Int, half: Int) {
        _uiState.update { it.copy(fullPriceCount = full, halfPriceCount = half) }
    }

    fun updateSnackQuantity(snackId: Int, delta: Int) {
        _uiState.update { state ->
            val currentQty = state.selectedSnacks.getOrDefault(snackId, 0)
            val newQty = (currentQty + delta).coerceAtLeast(0)
            val newSnacks = state.selectedSnacks.toMutableMap()
            if (newQty > 0) {
                newSnacks[snackId] = newQty
            } else {
                newSnacks.remove(snackId)
            }
            state.copy(selectedSnacks = newSnacks)
        }
    }

    fun getTotalSnackPrice(): Float {
        return _uiState.value.selectedSnacks.entries.sumOf { (id, qty) ->
            val snack = availableSnacks.find { it.id == id }
            (snack?.price ?: 0f).toDouble() * qty
        }.toFloat()
    }

    fun getTotalTicketPrice(): Float {
        return (_uiState.value.fullPriceCount * 40f) + (_uiState.value.halfPriceCount * 20f)
    }

    fun getTotalTicketCount(): Int {
        return _uiState.value.fullPriceCount + _uiState.value.halfPriceCount
    }
}
