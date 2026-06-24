package br.ufscar.cinemiranha.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.network.RetrofitClient
import br.ufscar.cinemiranha.repository.MovieRepository
import kotlinx.coroutines.launch

data class MovieDetailUiState(
    val movie: MovieResponse? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class MovieDetailViewModel(
    private val movieId: Long,
    private val movieRepository: MovieRepository
) : ViewModel() {

    var uiState by mutableStateOf(MovieDetailUiState())
        private set

    init {
        loadMovie()
    }

    fun loadMovie() {
        uiState = MovieDetailUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val movie = movieRepository.getMovie(movieId)
                uiState = MovieDetailUiState(movie = movie, isLoading = false)
            } catch (e: Exception) {
                uiState = MovieDetailUiState(
                    isLoading = false,
                    errorMessage = "Não foi possível carregar o filme.\n${e.message}"
                )
            }
        }
    }

    companion object {
        fun factory(movieId: Long): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                MovieDetailViewModel(movieId, MovieRepository(RetrofitClient.apiService)) as T
        }
    }
}
