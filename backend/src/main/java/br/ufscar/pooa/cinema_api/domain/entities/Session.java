package br.ufscar.pooa.cinema_api.domain.entities;

import br.ufscar.pooa.cinema_api.domain.enums.Format;
import br.ufscar.pooa.cinema_api.domain.enums.Subtitle;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Format format;

    @Column
    private LocalDateTime date;

    @Column
    private Subtitle subtitle;

    @Column
    private Integer priceInCents;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public boolean isSeatAvailable(Seat newSeat) {
        if ( newSeat == null ) return false;
        if ( room == null ) return true;
        if ( tickets == null ) return true;

        List<Seat> takenSeats = tickets.stream().map(Ticket::getSeat).toList();

        List<Seat> availableSeats = room.getRows().stream()
                .flatMap(row -> row.getSeats().stream())
                .filter(seat -> !takenSeats.contains(seat))
                .toList();

        return availableSeats.contains(newSeat);
    }

    public Session() {
    }

    public Long getId() {
        return id;
    }

    public Session setId(Long id) {
        this.id = id;
        return this;
    }

    public Format getFormat() {
        return format;
    }

    public Session setFormat(Format format) {
        this.format = format;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Session setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public Subtitle getSubtitle() {
        return subtitle;
    }

    public Session setSubtitle(Subtitle subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public Integer getPriceInCents() {
        return priceInCents;
    }

    public Session setPriceInCents(Integer priceInCents) {
        this.priceInCents = priceInCents;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Session setRoom(Room room) {
        this.room = room;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public Session setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Session setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(getId(), session.getId()) && getFormat() == session.getFormat() && Objects.equals(getDate(), session.getDate()) && getSubtitle() == session.getSubtitle() && Objects.equals(getPriceInCents(), session.getPriceInCents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFormat(), getDate(), getSubtitle(), getPriceInCents());
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", format=" + format +
                ", date=" + date +
                ", subtitle=" + subtitle +
                ", priceInCents=" + priceInCents +
                '}';
    }
}
