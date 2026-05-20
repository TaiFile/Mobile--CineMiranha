package br.ufscar.pooa.cinema_api.domain.repositories.room;

import br.ufscar.pooa.cinema_api.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoomRepository extends JpaRepository<Room, Long> {
}

