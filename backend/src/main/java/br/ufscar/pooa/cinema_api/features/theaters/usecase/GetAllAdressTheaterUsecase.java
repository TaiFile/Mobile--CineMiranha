package br.ufscar.pooa.cinema_api.features.theaters.usecase;

import br.ufscar.pooa.cinema_api.domain.entities.Address;
import br.ufscar.pooa.cinema_api.domain.repositories.address.IAddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllAdressTheaterUsecase implements IGetAllAddressTheaterUseCase {
    private final IAddressRepository addressRepository;
    public GetAllAdressTheaterUsecase(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> execute() {
        return addressRepository.findAll();
    }
}
