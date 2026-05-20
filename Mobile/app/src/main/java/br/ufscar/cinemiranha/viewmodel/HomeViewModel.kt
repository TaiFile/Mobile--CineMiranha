package br.ufscar.cinemiranha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val nowPlayingMovies: List<MovieResponse> = emptyList(),
    val comingSoonMovies: List<MovieResponse> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        _uiState.value = HomeUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val movies = RetrofitClient.apiService.getMovies()
                _uiState.value = HomeUiState(
                    nowPlayingMovies = movies.filter { it.status == "NOW_PLAYING" },
                    comingSoonMovies = movies.filter { it.status == "COMING_SOON" },
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState(
                    isLoading = false,
                    errorMessage = "Não foi possível carregar os filmes.\n${e.message}"
                )
            }
        }
    }
}
