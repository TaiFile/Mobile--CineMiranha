package br.ufscar.pooa.cinema_api.domain.repositories.address;

import br.ufscar.pooa.cinema_api.domain.entities.Address;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByStreetAndNumber(String street, String number);
}
