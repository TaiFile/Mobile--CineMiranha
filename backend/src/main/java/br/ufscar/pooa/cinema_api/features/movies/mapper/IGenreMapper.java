package br.ufscar.pooa.cinema_api.features.movies.mapper;

import br.ufscar.pooa.cinema_api.features.movies.dto.GenreDTO;
import br.ufscar.pooa.cinema_api.domain.entities.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IGenreMapper {
    GenreDTO toGenreDTO(Genre genre);
}

