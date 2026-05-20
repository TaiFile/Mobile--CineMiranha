package br.ufscar.pooa.cinema_api.domain.repositories.session;

import br.ufscar.pooa.cinema_api.domain.entities.Session;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ISessionRepository extends JpaRepository<Session, Long> {
    @Override
    @EntityGraph(attributePaths = {"room", "room.rows", "room.rows.seats", "tickets"})
    Optional<Session> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {"movie", "room", "room.rows", "room.rows.seats", "tickets"})
    List<Session> findAllByMovieId(Long movieId);

    @Query("SELECT s FROM Session s JOIN s.room r WHERE s.movie.id = :movieId AND r.theater.id = :theaterId")
    @EntityGraph(attributePaths = {"movie", "room"})
    List<Session> findAllByMovieIdAndTheaterId(@Param("movieId") Long movieId, @Param("theaterId") Long theaterId);
}

