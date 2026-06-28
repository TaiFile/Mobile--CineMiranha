package br.ufscar.cinemiranha.network

import br.ufscar.cinemiranha.model.dto.MovieResponse
import br.ufscar.cinemiranha.model.dto.SessionResponse
import br.ufscar.cinemiranha.model.dto.SnackResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("movies")
    suspend fun getMovies(): List<MovieResponse>

    @GET("movies/{id}")
    suspend fun getMovie(@Path("id") id: Long): MovieResponse

    @GET("movies/{id}/sessions")
    suspend fun getMovieSessions(@Path("id") id: Long): List<SessionResponse>

    @GET("snacks")
    suspend fun getSnacks(): List<SnackResponse>
}
