package br.ufscar.pooa.cinema_api.features.clients.mapper;

import br.ufscar.pooa.cinema_api.domain.entities.Client;
import br.ufscar.pooa.cinema_api.features.clients.dto.ClientResponseDTO;
import br.ufscar.pooa.cinema_api.features.clients.dto.RegisterClientRequestDTO;
import br.ufscar.pooa.cinema_api.features.tickets.mapper.ITicketMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {ITicketMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IClientMapper {

    ClientResponseDTO toClientResponseDTO(Client client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    Client toClient(RegisterClientRequestDTO registerClientRequestDTO);
}

