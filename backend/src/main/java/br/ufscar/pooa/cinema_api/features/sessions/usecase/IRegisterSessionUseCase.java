package br.ufscar.pooa.cinema_api.features.sessions.usecase;

import br.ufscar.pooa.cinema_api.features.sessions.dto.RegisterSessionRequestDTO;
import br.ufscar.pooa.cinema_api.features.sessions.dto.SessionResponseDTO;

public interface IRegisterSessionUseCase {
    SessionResponseDTO execute(RegisterSessionRequestDTO requestDTO);
}
