package br.ufscar.pooa.cinema_api.domain.entities;

import br.ufscar.pooa.cinema_api.domain.enums.Gender;
import br.ufscar.pooa.cinema_api.domain.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "managers")
public class Manager extends User {
    @Column
    private String cpf;

    @Column
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Manager() {
    }

    @Override
    public Manager setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public Manager setEmail(String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public Manager setPassword(String password) {
        super.setPassword(password);
        return this;
    }

    @Override
    public Manager setRole(Role role) {
        super.setRole(role);
        return this;
    }

    public String getCpf() {
        return cpf;
    }

    public Manager setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }


    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Manager setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Theater getTheater() {
        return theater;
    }

    public Manager setTheater(Theater theater) {
        this.theater = theater;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Manager setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Manager manager = (Manager) o;
        return Objects.equals(cpf, manager.cpf) && Objects.equals(birthDate, manager.birthDate) && Objects.equals(theater, manager.theater) && Objects.equals(gender, manager.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cpf, birthDate, theater, gender);
    }
}
