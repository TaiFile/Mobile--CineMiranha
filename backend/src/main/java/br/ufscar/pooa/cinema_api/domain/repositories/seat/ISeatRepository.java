package br.ufscar.pooa.cinema_api.domain.repositories.seat;

import br.ufscar.pooa.cinema_api.domain.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISeatRepository extends JpaRepository<Seat, Long> {

}

