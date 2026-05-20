package br.ufscar.cinemiranha.model

import java.time.LocalDateTime

data class SessionResponse(
    val id: Long,
    val roomId: Long,
    val roomName: String?,
    val movieId: Long,
    val format: String,
    val date: String,
    val subtitle: String,
    val priceInCents: Int?
) {
    fun timeLabel(): String {
        return try {
            val dt = LocalDateTime.parse(date)
            val h = dt.hour.toString().padStart(2, '0')
            val m = dt.minute.toString().padStart(2, '0')
            "$h:$m"
        } catch (e: Exception) {
            date.take(5)
        }
    }

    fun dateDayLabel(): String {
        return try {
            val dt = LocalDateTime.parse(date)
            val day = dt.dayOfMonth.toString().padStart(2, '0')
            val month = dt.monthValue.toString().padStart(2, '0')
            "$day/$month"
        } catch (e: Exception) { "" }
    }

    fun weekDayLabel(): String {
        return try {
            val dt = LocalDateTime.parse(date)
            when (dt.dayOfWeek.value) {
                1 -> "SEG"; 2 -> "TER"; 3 -> "QUA"; 4 -> "QUI"
                5 -> "SEX"; 6 -> "SAB"; 7 -> "DOM"; else -> ""
            }
        } catch (e: Exception) { "" }
    }

    fun formatLabel(): String = if (format == "THREE_D") "3D" else "2D"

    fun subtitleLabel(): String = when (subtitle) {
        "SUBTITLED" -> "LEG"
        "DUBBED"    -> "DUB"
        else        -> "ORG"
    }
}
