package br.ufscar.pooa.cinema_api.domain.entities;

import br.ufscar.pooa.cinema_api.domain.enums.RoomType;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Row> rows = new LinkedHashSet<>();

    @OneToMany(mappedBy = "room")
    private List<Session> sessions = new ArrayList<>();

    public Room() {
    }

    public Long getId() {
        return id;
    }

    public Room setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Room setName(String name) {
        this.name = name;
        return this;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Room setRoomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }

    public Theater getTheater() {
        return theater;
    }

    public Room setTheater(Theater theater) {
        this.theater = theater;
        return this;
    }

    public Set<Row> getRows() {
        return rows;
    }

    public Room setRows(Set<Row> rows) {
        this.rows = rows;
        return this;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public Room setSessions(List<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(getId(), room.getId()) && Objects.equals(getName(), room.getName()) && getRoomType() == room.getRoomType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRoomType());
    }

    @Override
    public String toString() {
        return String.format("Room{id=%d, name='%s', roomType=%s}", id, name, roomType);
    }
}
