package br.ufscar.pooa.cinema_api.features.rooms.usecase;

import br.ufscar.pooa.cinema_api.features.rooms.dto.RegisterRoomRequestDTO;
import br.ufscar.pooa.cinema_api.features.rooms.dto.RoomResponseDTO;

public interface IRegisterRoomUseCase {
    RoomResponseDTO execute(RegisterRoomRequestDTO requestDTO);
}
