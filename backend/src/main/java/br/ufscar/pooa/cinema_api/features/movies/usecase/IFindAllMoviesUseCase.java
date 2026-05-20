package br.ufscar.pooa.cinema_api.features.movies.usecase;

import br.ufscar.pooa.cinema_api.domain.entities.Movie;

import java.util.List;

public interface IFindAllMoviesUseCase {
    List<Movie> execute();
}
