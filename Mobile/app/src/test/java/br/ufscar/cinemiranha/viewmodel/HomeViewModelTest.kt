package br.ufscar.cinemiranha.viewmodel

import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: MovieRepository

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun movie(id: Long, status: String) = MovieResponse(
        id = id, title = "Movie $id", synopsis = null, coverUrl = null,
        trailerUrl = null, durationInSeconds = null, ageRating = null,
        status = status, sessionTimes = null, genreNames = null
    )

    @Test
    fun `loadMovies success filters movies by status`() = runTest {
        coEvery { repository.getMovies() } returns listOf(
            movie(1, "NOW_PLAYING"),
            movie(2, "COMING_SOON"),
            movie(3, "NOW_PLAYING")
        )

        val viewModel = HomeViewModel(repository)

        assertFalse(viewModel.uiState.isLoading)
        assertEquals(2, viewModel.uiState.nowPlayingMovies.size)
        assertEquals(1, viewModel.uiState.comingSoonMovies.size)
        assertNull(viewModel.uiState.errorMessage)
    }

    @Test
    fun `loadMovies failure sets error message`() = runTest {
        coEvery { repository.getMovies() } throws Exception("Network error")

        val viewModel = HomeViewModel(repository)

        assertFalse(viewModel.uiState.isLoading)
        assertNotNull(viewModel.uiState.errorMessage)
        assertTrue(viewModel.uiState.nowPlayingMovies.isEmpty())
        assertTrue(viewModel.uiState.comingSoonMovies.isEmpty())
    }

    @Test
    fun `loadMovies with empty list results in empty state`() = runTest {
        coEvery { repository.getMovies() } returns emptyList()

        val viewModel = HomeViewModel(repository)

        assertFalse(viewModel.uiState.isLoading)
        assertTrue(viewModel.uiState.nowPlayingMovies.isEmpty())
        assertTrue(viewModel.uiState.comingSoonMovies.isEmpty())
        assertNull(viewModel.uiState.errorMessage)
    }
}
