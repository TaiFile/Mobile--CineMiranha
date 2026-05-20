package br.ufscar.pooa.cinema_api.features.rooms.dto;

import br.ufscar.pooa.cinema_api.domain.enums.RoomType;

import java.util.List;

public class RegisterRoomRequestDTO {
    private Long theaterId;
    private String name;
    private RoomType roomType;
    private List<RowDTO> rows;

    public RegisterRoomRequestDTO() {
    }

    public Long getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(Long theaterId) {
        this.theaterId = theaterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public List<RowDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowDTO> rows) {
        this.rows = rows;
    }
}
