package br.ufscar.pooa.cinema_api.features.tickets.mapper;

import br.ufscar.pooa.cinema_api.domain.entities.Ticket;
import br.ufscar.pooa.cinema_api.features.sessions.mapper.ISessionMapper;
import br.ufscar.pooa.cinema_api.features.rooms.mapper.ISeatMapper;
import br.ufscar.pooa.cinema_api.features.tickets.dto.TicketResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ISessionMapper.class, ISeatMapper.class})
public interface ITicketMapper {

    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "seat.id", target = "seatId")
    TicketResponseDTO toTicketResponseDTO(Ticket ticket);
}

