package br.ufscar.pooa.cinema_api.features.sessions.usecase;

import br.ufscar.pooa.cinema_api.domain.entities.Session;
import br.ufscar.pooa.cinema_api.domain.repositories.session.ISessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllSessionsByMovieIdUseCase implements IFindAllSessionsByMovieIdUseCase {

    private final ISessionRepository sessionRepository;

    public FindAllSessionsByMovieIdUseCase(ISessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> execute(Long movieId) {
        return sessionRepository.findAllByMovieId(movieId);
    }
}

