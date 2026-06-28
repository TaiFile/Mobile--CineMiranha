package br.ufscar.cinemiranha.repository

import br.ufscar.cinemiranha.model.dto.CheckoutRequest
import br.ufscar.cinemiranha.model.dto.CheckoutResponse
import br.ufscar.cinemiranha.network.RetrofitClient

class CheckoutRepository {

    suspend fun checkout(request: CheckoutRequest): CheckoutResponse {
        return RetrofitClient.apiService.postCheckout(request)
    }
}
