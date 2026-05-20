package br.ufscar.pooa.cinema_api.features.movies.usecase;

import br.ufscar.pooa.cinema_api.domain.entities.Movie;
import br.ufscar.pooa.cinema_api.domain.repositories.movie.IMovieRepository;
import br.ufscar.pooa.cinema_api.features._shared.exceptions.ResourceAlreadyExistsException;
import br.ufscar.pooa.cinema_api.features.movies.dto.MovieResponseDTO;
import br.ufscar.pooa.cinema_api.features.movies.dto.RegisterMovieRequestDTO;
import br.ufscar.pooa.cinema_api.features.movies.mapper.IMovieMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RegisterMovieUseCase implements IRegisterMovieUseCase {

    private final IMovieRepository movieRepository;
    private final IMovieMapper IMovieMapper;

    public RegisterMovieUseCase(IMovieRepository movieRepository, IMovieMapper IMovieMapper) {
        this.movieRepository = movieRepository;
        this.IMovieMapper = IMovieMapper;
    }

    public MovieResponseDTO execute(RegisterMovieRequestDTO requestDTO) {
        Optional<Movie> movieFound = movieRepository.findByTitle(requestDTO.getTitle());

        if (movieFound.isPresent()) {
            throw new ResourceAlreadyExistsException("Movie", "title", requestDTO.getTitle());
        }

        Movie newMovie = IMovieMapper.toMovie(requestDTO);
        Movie savedMovie = movieRepository.save(newMovie);

        return IMovieMapper.toMovieResponseDTO(savedMovie);
    }
}
