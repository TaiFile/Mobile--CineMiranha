package br.ufscar.cinemiranha.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.ufscar.cinemiranha.model.Snack
import br.ufscar.cinemiranha.repository.SnackRepository
import kotlinx.coroutines.launch

data class SnacksUiState(
    val availableSnacks: List<Snack> = emptyList(),
    val selectedSnacks: Map<Int, Int> = emptyMap(), // snackId -> quantity
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class SnacksViewModel(private val snackRepository: SnackRepository) : ViewModel() {
    var uiState by mutableStateOf(SnacksUiState())
        private set

    init {
        loadSnacks()
    }

    fun loadSnacks() {
        uiState = SnacksUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val snacks = snackRepository.getSnacks()
                uiState = SnacksUiState(availableSnacks = snacks, isLoading = false)
            } catch (e: Exception) {
                uiState = SnacksUiState(
                    isLoading = false,
                    errorMessage = "Não foi possível carregar os snacks.\n${e.message}"
                )
            }
        }
    }

    fun updateSnackQuantity(snackId: Int, delta: Int) {
        val currentQty = uiState.selectedSnacks.getOrDefault(snackId, 0)
        val newQty = (currentQty + delta).coerceAtLeast(0)
        val newSnacks = uiState.selectedSnacks.toMutableMap()
        if (newQty > 0) {
            newSnacks[snackId] = newQty
        } else {
            newSnacks.remove(snackId)
        }
        uiState = uiState.copy(selectedSnacks = newSnacks)
    }

    fun getTotalSnackPrice(): Float {
        return uiState.selectedSnacks.entries.sumOf { (id, qty) ->
            val snack = uiState.availableSnacks.find { it.id == id }
            (snack?.price ?: 0f).toDouble() * qty
        }.toFloat()
    }

    fun reset() {
        uiState = uiState.copy(selectedSnacks = emptyMap())
    }

    companion object {
        fun factory(snackRepository: SnackRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    SnacksViewModel(snackRepository) as T
            }
    }
}
