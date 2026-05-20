package br.ufscar.pooa.cinema_api.features.sessions.usecase;

import br.ufscar.pooa.cinema_api.domain.entities.Session;
import br.ufscar.pooa.cinema_api.domain.repositories.session.ISessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllSessionsByMovieIdAndTheaterIdUseCase implements IFindAllSessionsByMovieIdAndTheaterIdUseCase {

    private final ISessionRepository sessionRepository;

    public FindAllSessionsByMovieIdAndTheaterIdUseCase(ISessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> execute(Long movieId, Long theaterId) {
        return sessionRepository.findAllByMovieIdAndTheaterId(movieId, theaterId);
    }
}

