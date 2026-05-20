package br.ufscar.pooa.cinema_api.features.managers.usecase;

import br.ufscar.pooa.cinema_api.features.managers.dto.ManagerResponseDTO;
import br.ufscar.pooa.cinema_api.features.managers.dto.RegisterManagerRequestDTO;

public interface IRegisterManagerUseCase {
    ManagerResponseDTO execute(RegisterManagerRequestDTO requestDTO);
}
