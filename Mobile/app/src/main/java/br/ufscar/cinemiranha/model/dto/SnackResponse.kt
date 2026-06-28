package br.ufscar.cinemiranha.model.dto

data class SnackResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Float,
    val category: String
)
