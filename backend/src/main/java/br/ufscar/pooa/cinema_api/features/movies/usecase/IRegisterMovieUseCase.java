package br.ufscar.pooa.cinema_api.features.movies.usecase;

import br.ufscar.pooa.cinema_api.features.movies.dto.RegisterMovieRequestDTO;
import br.ufscar.pooa.cinema_api.features.movies.dto.MovieResponseDTO;

public interface IRegisterMovieUseCase {
    MovieResponseDTO execute(RegisterMovieRequestDTO requestDTO);
}
