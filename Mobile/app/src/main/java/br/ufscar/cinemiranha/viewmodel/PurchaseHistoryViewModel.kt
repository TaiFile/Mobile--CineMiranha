package br.ufscar.cinemiranha.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.ufscar.cinemiranha.CinemaApplication
import br.ufscar.cinemiranha.database.PurchaseDao
import br.ufscar.cinemiranha.database.PurchaseEntity
import kotlinx.coroutines.launch

data class PurchaseHistoryUiState(
    val purchases: List<PurchaseEntity> = emptyList(),
    val isLoading: Boolean = true
)

class PurchaseHistoryViewModel(private val purchaseDao: PurchaseDao) : ViewModel() {

    var uiState by mutableStateOf(PurchaseHistoryUiState())
        private set

    init {
        loadPurchases()
    }

    fun loadPurchases() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = uiState.copy(purchases = purchaseDao.getAllPurchases(), isLoading = false)
        }
    }

    companion object {
        fun factory(applicationContext: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val dao = (applicationContext as CinemaApplication).database.purchaseDao()
                    return PurchaseHistoryViewModel(dao) as T
                }
            }
    }
}
