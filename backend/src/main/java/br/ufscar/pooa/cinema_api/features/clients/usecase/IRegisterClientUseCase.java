package br.ufscar.pooa.cinema_api.features.clients.usecase;

import br.ufscar.pooa.cinema_api.features.clients.dto.ClientResponseDTO;
import br.ufscar.pooa.cinema_api.features.clients.dto.RegisterClientRequestDTO;

public interface IRegisterClientUseCase {
    ClientResponseDTO execute(RegisterClientRequestDTO requestDTO);
}
