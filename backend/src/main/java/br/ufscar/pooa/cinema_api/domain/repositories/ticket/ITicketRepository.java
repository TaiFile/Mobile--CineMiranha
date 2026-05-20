package br.ufscar.pooa.cinema_api.domain.repositories.ticket;

import br.ufscar.pooa.cinema_api.domain.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {
}

