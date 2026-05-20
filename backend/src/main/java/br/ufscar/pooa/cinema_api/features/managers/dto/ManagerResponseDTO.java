package br.ufscar.pooa.cinema_api.features.managers.dto;

import br.ufscar.pooa.cinema_api.features.admins.dto.UserResponseDTO;
import br.ufscar.pooa.cinema_api.domain.entities.Theater;
import br.ufscar.pooa.cinema_api.domain.enums.Role;

import java.time.LocalDate;

public class ManagerResponseDTO extends UserResponseDTO {

    private String cpf;
    private LocalDate birthDate;
    private Theater theater;

    public ManagerResponseDTO() {
        super();
    }

    public ManagerResponseDTO(Long id, String email, String password, Role role, String cpf, LocalDate birthDate, Theater theater) {
        super(id, email, password, role);
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.theater = theater;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }
}
