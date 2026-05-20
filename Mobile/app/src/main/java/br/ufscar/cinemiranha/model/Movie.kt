package br.ufscar.cinemiranha.model

data class Movie(
    val id: Long,
    val title: String,
    val coverUrl: String,
    val durationInSeconds: Int? = null,
    val ageRating: AgeRating? = null,
    val status: MovieStatus,
    val releaseDate: String? = null
)

enum class AgeRating {
    GENERAL_AUDIENCE,
    TEN_YEARS,
    TWELVE_YEARS,
    FOURTEEN_YEARS,
    SIXTEEN_YEARS,
    EIGHTEEN_YEARS;

    fun toLabel(): String = when (this) {
        GENERAL_AUDIENCE -> "L"
        TEN_YEARS -> "10"
        TWELVE_YEARS -> "12"
        FOURTEEN_YEARS -> "14"
        SIXTEEN_YEARS -> "16"
        EIGHTEEN_YEARS -> "18"
    }
}

enum class MovieStatus {
    COMING_SOON,
    NOW_PLAYING,
    PAST_RELEASE
}
