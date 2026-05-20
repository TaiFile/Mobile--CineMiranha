package br.ufscar.pooa.cinema_api.features.rooms.mapper;

import br.ufscar.pooa.cinema_api.domain.entities.Address;
import br.ufscar.pooa.cinema_api.features.theaters.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IAddressMapper {

    AddressDTO toAddressDTO(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    @Mapping(target = "theater", ignore = true)
    Address toAddress(AddressDTO addressDTO);
}

