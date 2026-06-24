package br.ufscar.cinemiranha.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.network.RetrofitClient
import br.ufscar.cinemiranha.repository.MovieRepository
import kotlinx.coroutines.launch

data class HomeUiState(
    val nowPlayingMovies: List<MovieResponse> = emptyList(),
    val comingSoonMovies: List<MovieResponse> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadMovies()
    }

    fun loadMovies() {
        uiState = HomeUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val movies = movieRepository.getMovies()
                uiState = HomeUiState(
                    nowPlayingMovies = movies.filter { it.status == "NOW_PLAYING" },
                    comingSoonMovies = movies.filter { it.status == "COMING_SOON" },
                    isLoading = false
                )
            } catch (e: Exception) {
                uiState = HomeUiState(
                    isLoading = false,
                    errorMessage = "Não foi possível carregar os filmes.\n${e.message}"
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                HomeViewModel(MovieRepository(RetrofitClient.apiService)) as T
        }
    }
}
