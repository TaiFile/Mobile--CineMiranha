package br.ufscar.pooa.cinema_api.features.movies.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.pooa.cinema_api.domain.entities.Movie;
import br.ufscar.pooa.cinema_api.domain.entities.Session;
import br.ufscar.pooa.cinema_api.features.movies.dto.MovieResponseDTO;
import br.ufscar.pooa.cinema_api.features.movies.dto.RegisterMovieRequestDTO;
import br.ufscar.pooa.cinema_api.features.movies.mapper.IMovieMapper;
import br.ufscar.pooa.cinema_api.features.movies.usecase.IFindAllMoviesUseCase;
import br.ufscar.pooa.cinema_api.features.movies.usecase.IFindMovieByIdUseCase;
import br.ufscar.pooa.cinema_api.features.movies.usecase.IRegisterMovieUseCase;
import br.ufscar.pooa.cinema_api.features.sessions.dto.SessionResponseDTO;
import br.ufscar.pooa.cinema_api.features.sessions.mapper.ISessionMapper;
import br.ufscar.pooa.cinema_api.features.sessions.usecase.IFindAllSessionsByMovieIdUseCase;


@RestController
@RequestMapping("/movies")
public class MovieController {
    private final IRegisterMovieUseCase registerMovieUseCase;
    private final IFindAllMoviesUseCase findAllMoviesUseCase;
    private final IFindMovieByIdUseCase findMovieByIdUseCase;
    private final IFindAllSessionsByMovieIdUseCase findAllSessionsByMovieIdUseCase;
    private final IMovieMapper movieMapper;
    private final ISessionMapper sessionMapper;

    public MovieController(IRegisterMovieUseCase registerMovieUseCase, IFindAllMoviesUseCase findAllMoviesUseCase, IFindMovieByIdUseCase findMovieByIdUseCase,
        IFindAllSessionsByMovieIdUseCase findAllSessionsByMovieIdUseCase, IMovieMapper movieMapper, ISessionMapper sessionMapper) {
        this.registerMovieUseCase = registerMovieUseCase;
        this.findAllMoviesUseCase = findAllMoviesUseCase;
        this.findMovieByIdUseCase = findMovieByIdUseCase;
        this.findAllSessionsByMovieIdUseCase = findAllSessionsByMovieIdUseCase;
        this.movieMapper = movieMapper;
        this.sessionMapper = sessionMapper;
    }

    @PostMapping
    public ResponseEntity<MovieResponseDTO> register(@RequestBody RegisterMovieRequestDTO registerRequestBody) {
        var responseDTO = registerMovieUseCase.execute(registerRequestBody);
        URI uri = URI.create("/movies/" + responseDTO.getId());
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> findAll() {
        List<Movie> movies = findAllMoviesUseCase.execute();
        List<MovieResponseDTO> response = movies.stream()
                .map(movieMapper::toMovieResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> findById(@PathVariable Long id) {
        Movie movie = findMovieByIdUseCase.execute(id);
        return ResponseEntity.ok(movieMapper.toMovieResponseDTO(movie));
    }

    @GetMapping("/{id}/sessions")
    public ResponseEntity<List<SessionResponseDTO>> findAllSessionsByMovieId(@PathVariable Long id) {
        List<Session> sessions = findAllSessionsByMovieIdUseCase.execute(id);
        List<SessionResponseDTO> response = sessions.stream()
            .map(sessionMapper::toSessionResponseDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
