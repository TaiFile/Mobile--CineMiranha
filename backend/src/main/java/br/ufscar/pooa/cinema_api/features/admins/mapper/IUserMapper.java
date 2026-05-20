package br.ufscar.pooa.cinema_api.features.admins.mapper;

import br.ufscar.pooa.cinema_api.domain.entities.User;
import br.ufscar.pooa.cinema_api.features.admins.dto.RegisterUserRequestDTO;
import br.ufscar.pooa.cinema_api.features.admins.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    User toUser(RegisterUserRequestDTO registerUserRequestDTO);

    @Mapping(target = "id", ignore = true)
    UserResponseDTO toUserResponseDTO(User user);
}



