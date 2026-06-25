package br.ufscar.cinemiranha.repository

import android.content.Context
import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.Snack

class SnackRepository(private val context: Context) {

    suspend fun getSnacks(): List<Snack> = listOf(
        Snack(1, "Pipoca Salgada P", "250g de pipoca salgada", 19.99f, R.drawable.logo),
        Snack(2, "Pipoca Salgada M", "400g de pipoca salgada", 29.99f, R.drawable.logo),
        Snack(3, "Balde de pipoca salgada", "500g de pipoca salgada", 59.99f, R.drawable.logo),
        Snack(4, "Refrigerante 500ml", "Coca-cola, Fanta ou Sprite", 12.00f, R.drawable.logo),
        Snack(5, "Água Mineral 500ml", "Com ou sem gás", 6.00f, R.drawable.logo)
    )
}
