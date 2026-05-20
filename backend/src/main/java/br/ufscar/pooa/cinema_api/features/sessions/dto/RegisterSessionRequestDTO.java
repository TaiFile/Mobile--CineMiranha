package br.ufscar.pooa.cinema_api.features.sessions.dto;

import br.ufscar.pooa.cinema_api.domain.enums.Format;
import br.ufscar.pooa.cinema_api.domain.enums.Subtitle;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class RegisterSessionRequestDTO {
    @NotNull
    private Long roomId;

    @NotNull
    private Long movieId;

    @NotNull
    private Format format;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Subtitle subtitle;

    @NotNull
    private Integer priceInCents;

    public RegisterSessionRequestDTO() {
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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
}
