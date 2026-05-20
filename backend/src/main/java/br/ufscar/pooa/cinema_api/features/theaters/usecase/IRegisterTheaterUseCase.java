package br.ufscar.pooa.cinema_api.features.theaters.usecase;

import br.ufscar.pooa.cinema_api.features.theaters.dto.RegisterTheaterRequestDTO;
import br.ufscar.pooa.cinema_api.features.theaters.dto.TheaterResponseDTO;

public interface IRegisterTheaterUseCase {
    TheaterResponseDTO execute(String email, RegisterTheaterRequestDTO requestDTO);
}
