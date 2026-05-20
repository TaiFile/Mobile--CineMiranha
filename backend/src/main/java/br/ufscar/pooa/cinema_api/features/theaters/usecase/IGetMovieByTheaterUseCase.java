package br.ufscar.pooa.cinema_api.features.theaters.usecase;

import br.ufscar.pooa.cinema_api.domain.entities.Movie;

public interface IGetMovieByTheaterUseCase {
    Movie execute(Long id);
}
