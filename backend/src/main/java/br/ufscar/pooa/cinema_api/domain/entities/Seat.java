package br.ufscar.pooa.cinema_api.domain.entities;

import br.ufscar.pooa.cinema_api.domain.enums.SeatType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Character number;

    @Column
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @ManyToOne
    @JoinColumn(name = "row_id", nullable = false)
    private Row row;

    @OneToMany(mappedBy = "seat")
    private List<Ticket> tickets = new ArrayList<>();

    public Seat() {
    }

    public Long getId() {
        return id;
    }

    public Seat setId(Long id) {
        this.id = id;
        return this;
    }

    public Character getNumber() {
        return number;
    }

    public Seat setNumber(Character number) {
        this.number = number;
        return this;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public Seat setSeatType(SeatType seatType) {
        this.seatType = seatType;
        return this;
    }

    public Row getRow() {
        return row;
    }

    public Seat setRow(Row row) {
        this.row = row;
        return this;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Seat setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(getId(), seat.getId()) && Objects.equals(getNumber(), seat.getNumber()) && getSeatType() == seat.getSeatType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getSeatType());
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", number=" + number +
                ", seatType=" + seatType +
                '}';
    }
}
