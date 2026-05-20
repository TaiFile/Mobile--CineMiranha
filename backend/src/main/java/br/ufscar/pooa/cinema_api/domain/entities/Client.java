package br.ufscar.pooa.cinema_api.domain.entities;

import br.ufscar.pooa.cinema_api.domain.enums.Gender;
import br.ufscar.pooa.cinema_api.domain.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client extends User {
    @Column
    private String cpf;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private LocalDate birthDate;

    @OneToMany(mappedBy = "client")
    private List<Ticket> tickets = new ArrayList<>();

    public Client() {
    }

    @Override
    public Client setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public Client setEmail(String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public Client setPassword(String password) {
        super.setPassword(password);
        return this;
    }

    @Override
    public Client setRole(Role role) {
        super.setRole(role);
        return this;
    }

    public String getCpf() {
        return cpf;
    }

    public Client setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public String getName() {
        return name;
    }

    public Client setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Client setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Client setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Client setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Client setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return Objects.equals(getCpf(), client.getCpf()) && Objects.equals(getName(), client.getName()) && Objects.equals(getPhoneNumber(), client.getPhoneNumber()) && getGender() == client.getGender() && Objects.equals(getBirthDate(), client.getBirthDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCpf(), getName(), getPhoneNumber(), getGender(), getBirthDate());
    }
}
