package br.ufscar.cinemiranha.viewmodel

import androidx.lifecycle.ViewModel
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.Snack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SnacksUiState(
    val selectedSnacks: Map<Int, Int> = emptyMap() // snackId -> quantity
)

class SnacksViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SnacksUiState())
    val uiState: StateFlow<SnacksUiState> = _uiState.asStateFlow()

    val availableSnacks = listOf(
        Snack(1, "Pipoca Salgada P", "250g de pipoca salgada", 19.99f, R.drawable.logo),
        Snack(2, "Pipoca Salgada M", "400g de pipoca salgada", 29.99f, R.drawable.logo),
        Snack(3, "Balde de pipoca salgada", "500g de pipoca salgada", 59.99f, R.drawable.logo),
        Snack(4, "Refrigerante 500ml", "Coca-cola, Fanta ou Sprite", 12.00f, R.drawable.logo),
        Snack(5, "Água Mineral 500ml", "Com ou sem gás", 6.00f, R.drawable.logo)
    )

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

    fun reset() {
        _uiState.value = SnacksUiState()
    }
}
