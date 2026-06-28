package br.ufscar.cinemiranha

import android.app.Application
import br.ufscar.cinemiranha.database.CinemaDatabase

class CinemaApplication : Application() {
    val database: CinemaDatabase by lazy { CinemaDatabase.getDatabase(this) }
}
