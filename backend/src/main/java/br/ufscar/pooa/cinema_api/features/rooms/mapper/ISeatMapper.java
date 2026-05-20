package br.ufscar.pooa.cinema_api.features.rooms.mapper;

import br.ufscar.pooa.cinema_api.domain.entities.Seat;
import br.ufscar.pooa.cinema_api.features.theaters.dto.SeatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ISeatMapper {

    SeatDTO toSeatDTO(Seat seat);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "row", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    Seat toSeat(SeatDTO seatDTO);
}

