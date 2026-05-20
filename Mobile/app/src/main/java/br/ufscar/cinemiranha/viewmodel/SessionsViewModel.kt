package br.ufscar.cinemiranha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.model.SessionResponse
import br.ufscar.cinemiranha.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SessionsUiState(
    val movie: MovieResponse? = null,
    val sessions: List<SessionResponse> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedDate: String? = null,
    val selectedSubtitle: String? = null,
    val selectedFormat: String? = null
)

class SessionsViewModel(private val movieId: Long) : ViewModel() {

    private val _uiState = MutableStateFlow(SessionsUiState())
    val uiState: StateFlow<SessionsUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        _uiState.value = SessionsUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val movie = RetrofitClient.apiService.getMovie(movieId)
                val sessions = RetrofitClient.apiService.getMovieSessions(movieId)
                val firstDate = sessions.map { it.dateDayLabel() }.distinct().firstOrNull()
                _uiState.value = SessionsUiState(
                    movie = movie,
                    sessions = sessions,
                    isLoading = false,
                    selectedDate = firstDate
                )
            } catch (e: Exception) {
                _uiState.value = SessionsUiState(
                    isLoading = false,
                    errorMessage = "Não foi possível carregar as sessões.\n${e.message}"
                )
            }
        }
    }

    fun selectDate(date: String?) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
    }

    fun selectSubtitle(subtitle: String?) {
        _uiState.value = _uiState.value.copy(selectedSubtitle = subtitle)
    }

    fun selectFormat(format: String?) {
        _uiState.value = _uiState.value.copy(selectedFormat = format)
    }

    companion object {
        fun factory(movieId: Long): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                SessionsViewModel(movieId) as T
        }
    }
}
