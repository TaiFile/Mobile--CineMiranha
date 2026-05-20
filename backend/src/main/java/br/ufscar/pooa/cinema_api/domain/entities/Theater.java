package br.ufscar.pooa.cinema_api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "theaters")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String logoUrl;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", unique = true)
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "theater")
    private List<Manager> managers = new ArrayList<>();

    @Transient
    private Double distance;

    public Theater() {}

    public Long getId() {
        return id;
    }

    public Theater setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Theater setName(String name) {
        this.name = name;
        return this;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public Theater setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Theater setAddress(Address address) {
        this.address = address;
        return this;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Theater setRooms(List<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public Theater setManagers(List<Manager> managers) {
        this.managers = managers;
        return this;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theater theater = (Theater) o;
        return Objects.equals(id, theater.id) &&
                Objects.equals(name, theater.name) &&
                Objects.equals(logoUrl, theater.logoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, logoUrl);
    }

    @Override
    public String toString() {
        return String.format("Theater{id=%d, name='%s', logoUrl='%s'}", id, name, logoUrl);
    }
}
