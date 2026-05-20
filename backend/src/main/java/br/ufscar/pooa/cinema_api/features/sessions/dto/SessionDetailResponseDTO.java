package br.ufscar.pooa.cinema_api.features.sessions.dto;

import br.ufscar.pooa.cinema_api.domain.enums.Format;
import br.ufscar.pooa.cinema_api.domain.enums.Subtitle;
import br.ufscar.pooa.cinema_api.features.rooms.dto.RoomResponseDTO;
import br.ufscar.pooa.cinema_api.features.tickets.dto.TicketResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class SessionDetailResponseDTO {
    private Long id;
    private Long movieId;
    private Format format;
    private LocalDateTime date;
    private Subtitle subtitle;
    private Integer priceInCents;
    private RoomResponseDTO room;
    private List<TicketResponseDTO> tickets;

    public SessionDetailResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Subtitle getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(Subtitle subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getPriceInCents() {
        return priceInCents;
    }

    public void setPriceInCents(Integer priceInCents) {
        this.priceInCents = priceInCents;
    }

    public RoomResponseDTO getRoom() {
        return room;
    }

    public void setRoom(RoomResponseDTO room) {
        this.room = room;
    }

    public List<TicketResponseDTO> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketResponseDTO> tickets) {
        this.tickets = tickets;
    }
}

