package br.ufscar.cinemiranha.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.ufscar.cinemiranha.CinemaApplication
import br.ufscar.cinemiranha.model.dto.CheckoutRequest
import br.ufscar.cinemiranha.repository.CheckoutRepository
import kotlinx.coroutines.launch

data class OrderSummaryUiState(
    val isConfirming: Boolean = false,
    val confirmError: String? = null
)

class OrderSummaryViewModel(
    private val checkoutRepository: CheckoutRepository
) : ViewModel() {

    var uiState by mutableStateOf(OrderSummaryUiState())
        private set

    fun confirmPurchase(
        request: CheckoutRequest,
        movieTitle: String,
        sessionDate: String,
        sessionTime: String,
        onSuccess: () -> Unit
    ) {
        uiState = uiState.copy(isConfirming = true, confirmError = null)
        viewModelScope.launch {
            try {
                checkoutRepository.checkout(request, movieTitle, sessionDate, sessionTime)
                uiState = uiState.copy(isConfirming = false)
                onSuccess()
            } catch (e: Exception) {
                uiState = uiState.copy(isConfirming = false, confirmError = e.message)
            }
        }
    }

    companion object {
        fun factory(applicationContext: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val dao = (applicationContext as CinemaApplication).database.purchaseDao()
                    return OrderSummaryViewModel(CheckoutRepository(dao)) as T
                }
            }
    }
}
