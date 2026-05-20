package br.ufscar.pooa.cinema_api.features.theaters.usecase;

import org.springframework.stereotype.Service;

import br.ufscar.pooa.cinema_api.domain.entities.Movie;
import br.ufscar.pooa.cinema_api.domain.repositories.movie.IMovieRepository;

@Service
public class GetMovieByTheaterUsecase implements IGetMovieByTheaterUseCase {
    private final IMovieRepository movieRepository;
    public GetMovieByTheaterUsecase(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    @Override
    public Movie execute(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
    }
}
