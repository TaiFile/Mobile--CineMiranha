package br.ufscar.pooa.cinema_api.features.movies.mapper;

import br.ufscar.pooa.cinema_api.domain.entities.Movie;
import br.ufscar.pooa.cinema_api.features.movies.dto.MovieResponseDTO;
import br.ufscar.pooa.cinema_api.features.movies.dto.RegisterMovieRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IGenreMapper.class})
public interface IMovieMapper {

    @Mapping(target = "sessionTimes", ignore = true)
    @Mapping(target = "genreNames", ignore = true)
    MovieResponseDTO toMovieResponseDTO(Movie movie);

    @Mapping(target = "sessions", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "status", ignore = true)
    Movie toMovie(RegisterMovieRequestDTO registerMovieRequestDTO);
}

