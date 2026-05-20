package br.ufscar.pooa.cinema_api.features.theaters.usecase;

import java.util.List;

import br.ufscar.pooa.cinema_api.domain.entities.Address;

public interface IGetAllAddressTheaterUseCase {
    List<Address> execute();
}