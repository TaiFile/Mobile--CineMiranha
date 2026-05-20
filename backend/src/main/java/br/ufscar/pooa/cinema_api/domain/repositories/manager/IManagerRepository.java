package br.ufscar.pooa.cinema_api.domain.repositories.manager;

import br.ufscar.pooa.cinema_api.domain.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByCpf(String cpf);
}

