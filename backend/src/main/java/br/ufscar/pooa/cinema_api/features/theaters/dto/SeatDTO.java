package br.ufscar.pooa.cinema_api.features.theaters.dto;

import br.ufscar.pooa.cinema_api.domain.enums.SeatType;

public class SeatDTO {
    private Character number;
    private SeatType seatType;

    public SeatDTO() {
    }

    public Character getNumber() {
        return number;
    }

    public void setNumber(Character number) {
        this.number = number;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
}
