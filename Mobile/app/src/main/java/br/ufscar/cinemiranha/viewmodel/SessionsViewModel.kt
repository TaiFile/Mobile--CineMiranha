package br.ufscar.cinemiranha.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.model.dto.SessionResponse
import br.ufscar.cinemiranha.network.RetrofitClient
import br.ufscar.cinemiranha.repository.MovieRepository
import br.ufscar.cinemiranha.repository.SessionRepository
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

class SessionsViewModel(
    private val movieId: Long,
    private val movieRepository: MovieRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    var uiState by mutableStateOf(SessionsUiState())
        private set

    init {
        load()
    }

    fun load() {
        uiState = SessionsUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val movie = movieRepository.getMovie(movieId)
                val sessions = sessionRepository.getSessions(movieId)
                uiState = SessionsUiState(
                    movie = movie,
                    sessions = sessions,
                    isLoading = false
                )
            } catch (e: Exception) {
                uiState = SessionsUiState(
                    isLoading = false,
                    errorMessage = "Não foi possível carregar as sessões.\n${e.message}"
                )
            }
        }
    }

    fun selectDate(date: String?) {
        uiState = uiState.copy(selectedDate = date)
    }

    fun selectSubtitle(subtitle: String?) {
        uiState = uiState.copy(selectedSubtitle = subtitle)
    }

    fun selectFormat(format: String?) {
        uiState = uiState.copy(selectedFormat = format)
    }

    companion object {
        fun factory(movieId: Long): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                SessionsViewModel(
                    movieId,
                    MovieRepository(RetrofitClient.apiService),
                    SessionRepository(RetrofitClient.apiService)
                ) as T
        }
    }
}
