package br.ufscar.pooa.cinema_api.domain.repositories.user;

import br.ufscar.pooa.cinema_api.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

