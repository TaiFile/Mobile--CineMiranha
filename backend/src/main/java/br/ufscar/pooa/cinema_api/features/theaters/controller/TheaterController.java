package br.ufscar.pooa.cinema_api.features.theaters.controller;


import br.ufscar.pooa.cinema_api.domain.entities.Address;
import br.ufscar.pooa.cinema_api.domain.entities.Movie;
import br.ufscar.pooa.cinema_api.domain.entities.Session;
import br.ufscar.pooa.cinema_api.domain.entities.Theater;
import br.ufscar.pooa.cinema_api.features.movies.dto.MovieResponseDTO;
import br.ufscar.pooa.cinema_api.features.movies.mapper.IMovieMapper;
import br.ufscar.pooa.cinema_api.features.movies.usecase.IFindAllMoviesByTheaterIdUseCase;
import br.ufscar.pooa.cinema_api.features.sessions.dto.SessionResponseDTO;
import br.ufscar.pooa.cinema_api.features.sessions.mapper.ISessionMapper;
import br.ufscar.pooa.cinema_api.features.sessions.usecase.IFindAllSessionsByMovieIdAndTheaterIdUseCase;
import br.ufscar.pooa.cinema_api.features.theaters.dto.RegisterTheaterRequestDTO;
import br.ufscar.pooa.cinema_api.features.theaters.dto.TheaterResponseDTO;
import br.ufscar.pooa.cinema_api.features.theaters.mapper.ITheaterMapper;
import br.ufscar.pooa.cinema_api.features.theaters.usecase.IGetAllAddressTheaterUseCase;
import br.ufscar.pooa.cinema_api.features.theaters.usecase.IGetAllTheatersUsecase;
import br.ufscar.pooa.cinema_api.features.theaters.usecase.IGetTheatersByDistanceUseCase;
import br.ufscar.pooa.cinema_api.features.theaters.usecase.IRegisterTheaterUseCase;
import io.swagger.v3.oas.annotations.Operation;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/theaters")
@RestController
public class TheaterController {

    private final IRegisterTheaterUseCase registerTheaterUseCase;
    private final IGetTheatersByDistanceUseCase getTheatersByDistanceUseCase;
    private final IFindAllMoviesByTheaterIdUseCase findAllMoviesByTheaterIdUseCase;
    private final IFindAllSessionsByMovieIdAndTheaterIdUseCase findAllSessionsByMovieIdAndTheaterIdUseCase;
    private final IGetAllAddressTheaterUseCase getAllAddressTheaterUseCase;
    private final IGetAllTheatersUsecase getAllTheatersUsecase;
    private final ITheaterMapper theaterMapper;
    private final IMovieMapper movieMapper;
    private final ISessionMapper sessionMapper;

    public TheaterController(IRegisterTheaterUseCase registerTheaterUseCase,
        IGetTheatersByDistanceUseCase getTheatersByDistanceUseCase,
        IFindAllMoviesByTheaterIdUseCase findAllMoviesByTheaterIdUseCase,
        IFindAllSessionsByMovieIdAndTheaterIdUseCase findAllSessionsByMovieIdAndTheaterIdUseCase,
        IGetAllAddressTheaterUseCase getAllAddressTheaterUseCase,
        IGetAllTheatersUsecase getAllTheatersUsecase,
        ITheaterMapper theaterMapper,
        IMovieMapper movieMapper,
        ISessionMapper sessionMapper) {
        this.registerTheaterUseCase = registerTheaterUseCase;
        this.getTheatersByDistanceUseCase = getTheatersByDistanceUseCase;
        this.findAllMoviesByTheaterIdUseCase = findAllMoviesByTheaterIdUseCase;
        this.findAllSessionsByMovieIdAndTheaterIdUseCase = findAllSessionsByMovieIdAndTheaterIdUseCase;
        this.getAllAddressTheaterUseCase = getAllAddressTheaterUseCase;
        this.getAllTheatersUsecase = getAllTheatersUsecase;
        this.theaterMapper = theaterMapper;
        this.movieMapper = movieMapper;
        this.sessionMapper = sessionMapper;
    }

    @Operation(summary = "Register a new theater")
    @PostMapping
    public ResponseEntity<TheaterResponseDTO> register(
        @RequestParam String userEmail,
        @RequestBody RegisterTheaterRequestDTO registerRequestBody) {
        var responseDTO = registerTheaterUseCase.execute(userEmail, registerRequestBody);

        var uri = URI.create("/theaters/" + responseDTO.getId());
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping("/addresses")
    public List<Address> getAllAddressTheater() {
        return getAllAddressTheaterUseCase.execute();
    }

    @GetMapping("/list")
    public List<Theater> getAllTheaters() {
        return getAllTheatersUsecase.execute();
    }
    
    @Operation(summary = "Get all theaters sorted by distance from the user")
    @GetMapping("/by-distance")
    public ResponseEntity<List<TheaterResponseDTO>> getTheatersByDistance(
        @RequestParam("latitude") Double latitude,
        @RequestParam("longitude") Double longitude) {
        List<Theater> theaters = getTheatersByDistanceUseCase.execute(latitude, longitude);
        List<TheaterResponseDTO> response = theaters.stream()
            .map(theaterMapper::toTheaterResponseDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all movies for a specific theater by its ID")
    @GetMapping("/{id}/movies")
    public ResponseEntity<List<MovieResponseDTO>> findAllMoviesByTheaterId(@PathVariable Long id) {
        List<Movie> movies = findAllMoviesByTheaterIdUseCase.execute(id);
        List<MovieResponseDTO> response = movies.stream()
            .map(movieMapper::toMovieResponseDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all sessions for a specific movie at a specific theater")
    @GetMapping("/{theaterId}/movies/{movieId}/sessions")
    public ResponseEntity<List<SessionResponseDTO>> findAllSessionsByMovieIdAndTheaterId(
        @PathVariable Long theaterId,
        @PathVariable Long movieId) {
        List<Session> sessions = findAllSessionsByMovieIdAndTheaterIdUseCase.execute(movieId, theaterId);
        List<SessionResponseDTO> response = sessions.stream()
            .map(sessionMapper::toSessionResponseDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
