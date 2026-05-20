package br.ufscar.pooa.cinema_api.features.theaters.usecase;

import br.ufscar.pooa.cinema_api.domain.entities.Theater;

import java.util.List;

public interface IGetTheatersByDistanceUseCase {
    List<Theater> execute(Double userLatitude, Double userLongitude);
}
