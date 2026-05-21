package br.ufscar.cinemiranha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.ufscar.cinemiranha.model.MovieResponse
import br.ufscar.cinemiranha.network.RetrofitClient
import br.ufscar.cinemiranha.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        loadMovie()
    }

    fun loadMovie() {
        _uiState.value = MovieDetailUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val movie = movieRepository.getMovie(movieId)
                _uiState.value = MovieDetailUiState(movie = movie, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = MovieDetailUiState(
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
