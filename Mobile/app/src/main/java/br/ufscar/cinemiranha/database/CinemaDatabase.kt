package br.ufscar.cinemiranha.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PurchaseEntity::class], version = 1, exportSchema = false)
abstract class CinemaDatabase : RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao

    companion object {
        @Volatile
        private var Instance: CinemaDatabase? = null

        fun getDatabase(context: Context): CinemaDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CinemaDatabase::class.java, "cinema_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
