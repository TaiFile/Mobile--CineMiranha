package br.ufscar.pooa.cinema_api.domain.repositories.row;

import br.ufscar.pooa.cinema_api.domain.entities.Row;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRowRepository extends JpaRepository<Row, Long> {
}

