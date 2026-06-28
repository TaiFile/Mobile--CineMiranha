package br.ufscar.cinemiranha.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchases")
data class PurchaseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieTitle: String,
    val sessionDate: String,
    val sessionTime: String,
    val seats: String,
    val fullTickets: Int,
    val halfTickets: Int,
    val totalPrice: Float,
    val purchasedAt: String
)
