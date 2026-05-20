package br.ufscar.pooa.cinema_api.features.sessions.mapper;

import br.ufscar.pooa.cinema_api.features.rooms.mapper.IRoomMapper;
import br.ufscar.pooa.cinema_api.features.tickets.mapper.ITicketMapper;
import br.ufscar.pooa.cinema_api.domain.entities.Session;
import br.ufscar.pooa.cinema_api.features.movies.mapper.IMovieMapper;
import br.ufscar.pooa.cinema_api.features.sessions.dto.SessionDetailResponseDTO;
import br.ufscar.pooa.cinema_api.features.sessions.dto.SessionResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IMovieMapper.class, IRoomMapper.class, ITicketMapper.class})
public interface ISessionMapper {

    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "room.id", target = "roomId")
    SessionResponseDTO toSessionResponseDTO(Session session);

    @Mapping(source = "movie.id", target = "movieId")
    SessionDetailResponseDTO toSessionDetailResponseDTO(Session session);
}

