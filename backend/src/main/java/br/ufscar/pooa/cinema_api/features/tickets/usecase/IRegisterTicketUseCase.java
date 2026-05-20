package br.ufscar.pooa.cinema_api.features.tickets.usecase;

import br.ufscar.pooa.cinema_api.features.tickets.dto.RegisterTicketRequestDTO;
import br.ufscar.pooa.cinema_api.features.tickets.dto.TicketResponseDTO;

public interface IRegisterTicketUseCase {
    TicketResponseDTO execute(RegisterTicketRequestDTO requestDTO);

}
