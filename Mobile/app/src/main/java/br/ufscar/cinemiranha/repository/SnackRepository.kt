package br.ufscar.cinemiranha.repository

import br.ufscar.cinemiranha.R
import br.ufscar.cinemiranha.model.Snack
import br.ufscar.cinemiranha.network.RetrofitClient

class SnackRepository {

    suspend fun getSnacks(): List<Snack> {
        return RetrofitClient.apiService.getSnacks().map { dto ->
            Snack(
                id = dto.id,
                name = dto.name,
                description = dto.description,
                price = dto.price,
                imageRes = R.drawable.logo
            )
        }
    }
}
