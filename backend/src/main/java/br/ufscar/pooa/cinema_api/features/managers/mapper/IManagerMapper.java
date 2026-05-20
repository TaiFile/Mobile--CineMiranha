package br.ufscar.pooa.cinema_api.features.managers.mapper;

import br.ufscar.pooa.cinema_api.domain.entities.Manager;
import br.ufscar.pooa.cinema_api.features.managers.dto.ManagerResponseDTO;
import br.ufscar.pooa.cinema_api.features.managers.dto.RegisterManagerRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IManagerMapper {

    ManagerResponseDTO toManagerResponseDTO(Manager manager);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "theater", ignore = true)
    @Mapping(target = "gender", ignore = true)
    Manager toManager(RegisterManagerRequestDTO registerManagerRequestDTO);
}

