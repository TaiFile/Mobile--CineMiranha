package br.ufscar.pooa.cinema_api.features.admins.usecase;

import br.ufscar.pooa.cinema_api.features.admins.dto.RegisterUserRequestDTO;
import br.ufscar.pooa.cinema_api.features.admins.dto.UserResponseDTO;

public interface IRegisterUserUseCase {
    UserResponseDTO execute(RegisterUserRequestDTO requestDTO);
}
