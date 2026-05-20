package br.ufscar.pooa.cinema_api.domain.entities;

import br.ufscar.pooa.cinema_api.domain.enums.AgeRating;
import br.ufscar.pooa.cinema_api.domain.enums.MovieStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    @Column(length = 500)
    private String coverUrl;

    @Column(length = 500)
    private String trailerUrl;

    @Column
    private Integer durationInSeconds;

    @OneToMany(mappedBy = "movie")
    private List<Session> sessions = new ArrayList<>();

    @Column
    private AgeRating ageRating;

    @Column
    @Enumerated(EnumType.STRING)
    private MovieStatus status;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "movie_genres",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public Movie setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Movie setSynopsis(String synopsis) {
        this.synopsis = synopsis;
        return this;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public Movie setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        return this;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public Movie setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
        return this;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    public Movie setDurationInSeconds(Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
        return this;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public Movie setSessions(List<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Movie setGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public AgeRating getAgeRating() {
        return ageRating;
    }

    public Movie setAgeRating(AgeRating ageRating) {
        this.ageRating = ageRating;
        return this;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public Movie setStatus(MovieStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return Objects.equals(getId(), movie.getId()) && Objects.equals(getTitle(),
            movie.getTitle()) && Objects.equals(getSynopsis(), movie.getSynopsis())
            && Objects.equals(getCoverUrl(), movie.getCoverUrl()) && Objects.equals(getTrailerUrl(),
            movie.getTrailerUrl()) && Objects.equals(getDurationInSeconds(),
            movie.getDurationInSeconds()) && getAgeRating() == movie.getAgeRating()
            && getStatus() == movie.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getSynopsis(), getCoverUrl(), getTrailerUrl(),
            getDurationInSeconds(), getAgeRating(), getStatus());
    }
}
