package br.ufscar.cinemiranha.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.ufscar.cinemiranha.model.dto.CheckoutRequest
import br.ufscar.cinemiranha.repository.CheckoutRepository
import kotlinx.coroutines.launch

data class OrderSummaryUiState(
    val isConfirming: Boolean = false,
    val confirmError: String? = null,
    val confirmed: Boolean = false
)

class OrderSummaryViewModel(
    private val checkoutRepository: CheckoutRepository = CheckoutRepository()
) : ViewModel() {

    var uiState by mutableStateOf(OrderSummaryUiState())
        private set

    fun confirmPurchase(request: CheckoutRequest, onSuccess: () -> Unit) {
        uiState = uiState.copy(isConfirming = true, confirmError = null)
        viewModelScope.launch {
            try {
                checkoutRepository.checkout(request)
                uiState = uiState.copy(isConfirming = false, confirmed = true)
                onSuccess()
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isConfirming = false,
                    confirmError = e.message
                )
            }
        }
    }
}
