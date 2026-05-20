package br.ufscar.pooa.cinema_api.features.sessions.usecase;

import br.ufscar.pooa.cinema_api.domain.entities.Session;

import java.util.List;

public interface IFindAllSessionsByMovieIdAndTheaterIdUseCase {
    List<Session> execute(Long movieId, Long theaterId);
}

