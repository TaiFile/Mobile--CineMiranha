package br.ufscar.pooa.cinema_api.features.rooms.mapper;

import br.ufscar.pooa.cinema_api.domain.entities.Row;
import br.ufscar.pooa.cinema_api.features.rooms.dto.RowDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ISeatMapper.class})
public interface IRowMapper {
    RowDTO toRowDTO(Row row);
}

