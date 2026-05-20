package br.ufscar.pooa.cinema_api.features.movies.dto;

import br.ufscar.pooa.cinema_api.domain.enums.AgeRating;
import br.ufscar.pooa.cinema_api.domain.enums.MovieStatus;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MovieResponseDTO {

    private Long id;
    private String title;
    private String synopsis;
    private String coverUrl;
    private String trailerUrl;
    private Integer durationInSeconds;
    private AgeRating ageRating;
    private MovieStatus status;
    private List<String> sessionTimes;
    private Set<String> genreNames;

    public MovieResponseDTO() {
    }

    public MovieResponseDTO(Long id, String title, String synopsis, String coverUrl,
        String trailerUrl, Integer durationInSeconds, AgeRating ageRating, MovieStatus status,
        List<String> sessionTimes, Set<String> genreNames) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.coverUrl = coverUrl;
        this.trailerUrl = trailerUrl;
        this.durationInSeconds = durationInSeconds;
        this.ageRating = ageRating;
        this.status = status;
        this.sessionTimes = sessionTimes;
        this.genreNames = genreNames;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public AgeRating getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(AgeRating ageRating) {
        this.ageRating = ageRating;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public void setStatus(MovieStatus status) {
        this.status = status;
    }

    public List<String> getSessionTimes() {
        return sessionTimes;
    }

    public void setSessionTimes(List<String> sessionTimes) {
        this.sessionTimes = sessionTimes;
    }

    public Set<String> getGenreNames() {
        return genreNames;
    }

    public void setGenreNames(Set<String> genreNames) {
        this.genreNames = genreNames;
    }

    @Override
    public String toString() {
        return "MovieResponseDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", synopsis='" + synopsis + '\'' +
            ", coverUrl='" + coverUrl + '\'' +
            ", trailerUrl='" + trailerUrl + '\'' +
            ", durationInSeconds=" + durationInSeconds +
            ", ageRating=" + ageRating +
            ", sessionTimes=" + sessionTimes +
            ", genreNames=" + genreNames +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieResponseDTO that = (MovieResponseDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(synopsis, that.synopsis) &&
            Objects.equals(coverUrl, that.coverUrl) &&
            Objects.equals(trailerUrl, that.trailerUrl) &&
            Objects.equals(durationInSeconds, that.durationInSeconds) &&
            ageRating == that.ageRating &&
            Objects.equals(sessionTimes, that.sessionTimes) &&
            Objects.equals(genreNames, that.genreNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, synopsis, coverUrl, trailerUrl, durationInSeconds, ageRating,
            sessionTimes, genreNames);
    }
}
