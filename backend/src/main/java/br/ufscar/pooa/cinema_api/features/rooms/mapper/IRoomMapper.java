package br.ufscar.pooa.cinema_api.features.rooms.mapper;

import br.ufscar.pooa.cinema_api.domain.entities.Room;
import br.ufscar.pooa.cinema_api.features.rooms.dto.RoomResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IRowMapper.class})
public interface IRoomMapper {
    @Mapping(source = "theater.id", target = "theaterId")
    RoomResponseDTO toRoomResponseDTO(Room room);
}

