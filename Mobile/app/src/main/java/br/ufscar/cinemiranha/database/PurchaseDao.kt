package br.ufscar.cinemiranha.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PurchaseDao {

    @Insert
    suspend fun insert(purchase: PurchaseEntity)

    @Query("SELECT * FROM purchases ORDER BY id DESC")
    suspend fun getAllPurchases(): List<PurchaseEntity>
}
