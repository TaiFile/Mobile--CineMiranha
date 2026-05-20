package br.ufscar.pooa.cinema_api.features.tickets.usecase;

import br.ufscar.pooa.cinema_api.features._shared.exceptions.BadRequestException;
import br.ufscar.pooa.cinema_api.features._shared.exceptions.ResourceNotFoundException;
import br.ufscar.pooa.cinema_api.features._shared.gateways.payment.PaymentStrategyFactory;
import br.ufscar.pooa.cinema_api.features.tickets.dto.RegisterTicketRequestDTO;
import br.ufscar.pooa.cinema_api.features.tickets.dto.TicketResponseDTO;
import br.ufscar.pooa.cinema_api.features.tickets.mapper.ITicketMapper;
import br.ufscar.pooa.cinema_api.features._shared.gateways.payment.IPaymentStrategy;
import br.ufscar.pooa.cinema_api.domain.repositories.client.IClientRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.seat.ISeatRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.session.ISessionRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.ticket.ITicketRepository;
import br.ufscar.pooa.cinema_api.domain.entities.Client;
import br.ufscar.pooa.cinema_api.domain.entities.Seat;
import br.ufscar.pooa.cinema_api.domain.entities.Session;
import br.ufscar.pooa.cinema_api.domain.entities.Ticket;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RegisterTicketUseCase implements IRegisterTicketUseCase {

    private final ITicketRepository ticketRepository;
    private final ISessionRepository sessionRepository;
    private final ISeatRepository seatRepository;
    private final IClientRepository clientRepository;
    private final PaymentStrategyFactory paymentStrategyFactory;
    private final ITicketMapper ITicketMapper;

    public RegisterTicketUseCase(ITicketRepository ticketRepository,
                                 ISessionRepository sessionRepository,
                                 ISeatRepository seatRepository,
                                 IClientRepository clientRepository,
                                 PaymentStrategyFactory paymentStrategyFactory,
                                 ITicketMapper ITicketMapper) {
        this.ticketRepository = ticketRepository;
        this.sessionRepository = sessionRepository;
        this.seatRepository = seatRepository;
        this.clientRepository = clientRepository;
        this.paymentStrategyFactory = paymentStrategyFactory;
        this.ITicketMapper = ITicketMapper;
    }

    @Override
    public TicketResponseDTO execute(RegisterTicketRequestDTO requestDTO) {
        Session session = sessionRepository.findById(requestDTO.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Sessão", "id", requestDTO.getSessionId().toString()));

        Seat seat = seatRepository.findById(requestDTO.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("Assento", "id", requestDTO.getSeatId().toString()));

        Client client = clientRepository.findById(requestDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", requestDTO.getClientId().toString()));

        IPaymentStrategy paymentStrategy = paymentStrategyFactory.getStrategy(requestDTO.getPaymentMethod())
                .orElseThrow(() -> new IllegalArgumentException("Payment method not supported."));

        if (!session.isSeatAvailable(seat)) {
            throw new BadRequestException("Seat is not available.");
        }

        boolean paymentSuccessful = paymentStrategy.pay(requestDTO.getPriceInCentsClient());
        if (!paymentSuccessful) {
            throw new RuntimeException("Payment failed.");
        }

        Ticket newTicket = new Ticket();
        newTicket.setClient(client);
        newTicket.setSession(session);
        newTicket.setSeat(seat);
        newTicket.setPaymentMethod(requestDTO.getPaymentMethod());
        newTicket.setPriceInCents(requestDTO.getPriceInCentsClient());
        newTicket.setPaymentDate(Instant.now());

        Ticket savedTicket = ticketRepository.save(newTicket);

        return ITicketMapper.toTicketResponseDTO(savedTicket);
    }
}
