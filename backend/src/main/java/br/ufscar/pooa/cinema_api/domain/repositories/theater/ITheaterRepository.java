package br.ufscar.pooa.cinema_api.domain.repositories.theater;

import br.ufscar.pooa.cinema_api.domain.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITheaterRepository extends JpaRepository<Theater, Long> {
    Optional<Theater> findByName(String name);
}

