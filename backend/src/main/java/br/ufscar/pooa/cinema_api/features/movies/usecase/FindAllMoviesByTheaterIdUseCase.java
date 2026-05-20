package br.ufscar.pooa.cinema_api.features.movies.usecase;

import br.ufscar.pooa.cinema_api.domain.entities.Movie;
import br.ufscar.pooa.cinema_api.domain.repositories.movie.IMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllMoviesByTheaterIdUseCase implements IFindAllMoviesByTheaterIdUseCase {

    private final IMovieRepository movieRepository;

    public FindAllMoviesByTheaterIdUseCase(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> execute(Long theaterId) {
        return movieRepository.findAllByTheaterId(theaterId);
    }
}

