package br.ufscar.cinemiranha.network

import br.ufscar.cinemiranha.model.MovieResponse
import retrofit2.http.GET

interface ApiService {

    @GET("movies")
    suspend fun getMovies(): List<MovieResponse>

    @GET("movies/{id}")
    suspend fun getMovie(@retrofit2.http.Path("id") id: Long): MovieResponse
}
