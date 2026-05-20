package br.ufscar.pooa.cinema_api.features.rooms.usecase;

import br.ufscar.pooa.cinema_api.domain.entities.Room;
import br.ufscar.pooa.cinema_api.domain.entities.Row;
import br.ufscar.pooa.cinema_api.domain.entities.Seat;
import br.ufscar.pooa.cinema_api.domain.entities.Theater;
import br.ufscar.pooa.cinema_api.domain.repositories.room.IRoomRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.theater.ITheaterRepository;
import br.ufscar.pooa.cinema_api.features.rooms.dto.RegisterRoomRequestDTO;
import br.ufscar.pooa.cinema_api.features.rooms.dto.RoomResponseDTO;
import br.ufscar.pooa.cinema_api.features.rooms.mapper.IRoomMapper;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RegisterRoomUseCase implements IRegisterRoomUseCase {

    private final IRoomRepository repository;
    private final ITheaterRepository theaterRepository;
    private final IRoomMapper IRoomMapper;


    public RegisterRoomUseCase(IRoomRepository repository, ITheaterRepository theaterRepository,
        IRoomMapper IRoomMapper) {
        this.repository = repository;
        this.theaterRepository = theaterRepository;
        this.IRoomMapper = IRoomMapper;
    }

    @Override
    public RoomResponseDTO execute(RegisterRoomRequestDTO requestDTO) {
        Theater theater = theaterRepository.findById(requestDTO.getTheaterId())
            .orElseThrow(() -> new IllegalArgumentException("Theater not found."));

        Set<Row> rows = requestDTO.getRows().stream()
            .map(rowDTO -> {
                Row row = new Row();
                row.setLetter(rowDTO.getLetter());
                Set<Seat> seats = rowDTO.getSeats().stream()
                    .map(seatDTO -> {
                        Seat seat = new Seat();
                        seat.setNumber(seatDTO.getNumber());
                        seat.setSeatType(seatDTO.getSeatType());
                        seat.setRow(row);
                        return seat;
                    })
                    .collect(Collectors.toSet());
                row.setSeats(seats);
                row.setRoom(null); // The room is set later
                return row;
            })
            .collect(Collectors.toSet());

        Room room = new Room();
        room.setName(requestDTO.getName());
        room.setRoomType(requestDTO.getRoomType());
        room.setTheater(theater);
        room.setRows(rows);

        for (Row row : rows) {
            row.setRoom(room);
        }

        Room savedRoom = repository.save(room);

        return IRoomMapper.toRoomResponseDTO(savedRoom);
    }
}
