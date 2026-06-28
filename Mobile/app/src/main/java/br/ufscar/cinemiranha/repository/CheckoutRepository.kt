package br.ufscar.cinemiranha.repository

import br.ufscar.cinemiranha.database.PurchaseDao
import br.ufscar.cinemiranha.database.PurchaseEntity
import br.ufscar.cinemiranha.model.dto.CheckoutRequest
import br.ufscar.cinemiranha.model.dto.CheckoutResponse
import br.ufscar.cinemiranha.network.RetrofitClient
import java.time.Instant

class CheckoutRepository(private val purchaseDao: PurchaseDao) {

    suspend fun checkout(
        request: CheckoutRequest,
        movieTitle: String,
        sessionDate: String,
        sessionTime: String
    ): CheckoutResponse {
        val response = RetrofitClient.apiService.postCheckout(request)

        purchaseDao.insert(
            PurchaseEntity(
                movieTitle   = movieTitle,
                sessionDate  = sessionDate,
                sessionTime  = sessionTime,
                seats        = request.seats.joinToString(", "),
                fullTickets  = request.fullTickets,
                halfTickets  = request.halfTickets,
                totalPrice   = request.totalPrice,
                purchasedAt  = Instant.now().toString()
            )
        )

        return response
    }
}
