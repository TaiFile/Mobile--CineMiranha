package br.ufscar.cinemiranha.model

data class MovieResponse(
    val id: Long,
    val title: String,
    val synopsis: String?,
    val coverUrl: String?,
    val trailerUrl: String?,
    val durationInSeconds: Int?,
    val ageRating: String?,
    val status: String,
    val sessionTimes: List<String>?,
    val genreNames: Set<String>?
) {
    fun durationLabel(): String? {
        return durationInSeconds?.let { "${it / 60} min" }
    }

    fun ageRatingLabel(): String? = when (ageRating) {
        "GENERAL_AUDIENCE" -> "L"
        "TEN_YEARS"        -> "10"
        "TWELVE_YEARS"     -> "12"
        "FOURTEEN_YEARS"   -> "14"
        "SIXTEEN_YEARS"    -> "16"
        "EIGHTEEN_YEARS"   -> "18"
        else               -> null
    }

    fun firstSessionDate(): String? = sessionTimes?.firstOrNull()
}
