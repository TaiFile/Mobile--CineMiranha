package br.ufscar.cinemiranha.repository

import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.network.ApiService

class MovieRepository(private val api: ApiService) {

    suspend fun getMovies(): List<MovieResponse> = api.getMovies()

    suspend fun getMovie(id: Long): MovieResponse = api.getMovie(id)
}
