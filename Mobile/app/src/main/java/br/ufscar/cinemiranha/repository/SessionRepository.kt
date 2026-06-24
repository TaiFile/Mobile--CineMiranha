package br.ufscar.cinemiranha.repository

import br.ufscar.cinemiranha.model.dto.SessionResponse
import br.ufscar.cinemiranha.network.ApiService

class SessionRepository(private val api: ApiService) {

    suspend fun getSessions(movieId: Long): List<SessionResponse> = api.getMovieSessions(movieId)
}
