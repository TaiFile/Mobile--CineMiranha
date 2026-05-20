package br.ufscar.pooa.cinema_api.features.sessions.usecase;

import br.ufscar.pooa.cinema_api.features.sessions.dto.SessionDetailResponseDTO;

public interface IFindSessionByIdUseCase {
    SessionDetailResponseDTO execute(Long sessionId);
}

