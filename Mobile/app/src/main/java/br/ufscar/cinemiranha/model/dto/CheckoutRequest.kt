package br.ufscar.cinemiranha.model.dto

data class CheckoutRequest(
    val sessionId: Long,
    val seats: List<String>,
    val fullTickets: Int,
    val halfTickets: Int,
    val totalPrice: Float
)
