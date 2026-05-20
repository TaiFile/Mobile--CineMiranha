package br.ufscar.pooa.cinema_api.features.sessions.usecase;

import br.ufscar.pooa.cinema_api.features.sessions.dto.RegisterSessionRequestDTO;
import br.ufscar.pooa.cinema_api.features.sessions.dto.SessionResponseDTO;
import br.ufscar.pooa.cinema_api.features.sessions.mapper.ISessionMapper;
import br.ufscar.pooa.cinema_api.domain.repositories.movie.IMovieRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.room.IRoomRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.session.ISessionRepository;
import br.ufscar.pooa.cinema_api.domain.entities.Movie;
import br.ufscar.pooa.cinema_api.domain.entities.Room;
import br.ufscar.pooa.cinema_api.domain.entities.Session;
import org.springframework.stereotype.Service;

@Service
public class RegisterSessionUseCase implements IRegisterSessionUseCase {
    private final ISessionRepository sessionRepository;
    private final IRoomRepository repository;
    private final IMovieRepository movieRepository;
    private final ISessionMapper ISessionMapper;

    public RegisterSessionUseCase(ISessionRepository sessionRepository, IRoomRepository repository, IMovieRepository movieRepository, ISessionMapper ISessionMapper) {
        this.sessionRepository = sessionRepository;
        this.repository = repository;
        this.movieRepository = movieRepository;
        this.ISessionMapper = ISessionMapper;
    }

    @Override
    public SessionResponseDTO execute(RegisterSessionRequestDTO requestDTO) {
        Room room = repository.findById(requestDTO.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found."));

        Movie movie = movieRepository.findById(requestDTO.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found."));

        Session session = new Session();
        session.setMovie(movie);
        session.setRoom(room);
        session.setFormat(requestDTO.getFormat());
        session.setDate(requestDTO.getDate());
        session.setSubtitle(requestDTO.getSubtitle());
        session.setPriceInCents(requestDTO.getPriceInCents());

        Session savedSession = sessionRepository.save(session);

        return ISessionMapper.toSessionResponseDTO(savedSession);
    }
}
