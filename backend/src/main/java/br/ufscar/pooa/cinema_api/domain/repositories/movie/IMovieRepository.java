package br.ufscar.pooa.cinema_api.domain.repositories.movie;

import br.ufscar.pooa.cinema_api.domain.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IMovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);

    @Query("""
        SELECT DISTINCT m
        FROM Movie m
        JOIN m.sessions s JOIN s.room r
        WHERE r.theater.id = :theaterId""")
    List<Movie> findAllByTheaterId(@Param("theaterId") Long theaterId);
}

