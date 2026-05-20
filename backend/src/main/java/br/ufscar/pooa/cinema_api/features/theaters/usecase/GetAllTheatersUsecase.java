package br.ufscar.pooa.cinema_api.features.theaters.usecase;

import java.util.List;

import br.ufscar.pooa.cinema_api.domain.repositories.theater.ITheaterRepository;
import org.springframework.stereotype.Service;

import br.ufscar.pooa.cinema_api.domain.entities.Theater;

@Service
public class GetAllTheatersUsecase implements IGetAllTheatersUsecase {
    private final ITheaterRepository theaterRepository;
    public GetAllTheatersUsecase(ITheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    @Override
    public List<Theater> execute() {
        return theaterRepository.findAll();
    }
}
