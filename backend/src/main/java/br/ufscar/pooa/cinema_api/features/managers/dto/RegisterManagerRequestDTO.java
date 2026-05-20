package br.ufscar.pooa.cinema_api.features.managers.dto;

import br.ufscar.pooa.cinema_api.features.admins.dto.RegisterUserRequestDTO;
import br.ufscar.pooa.cinema_api.domain.enums.Role;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class RegisterManagerRequestDTO extends RegisterUserRequestDTO {

    @NotNull
    private String cpf;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private Long theaterId;

    public RegisterManagerRequestDTO() { super(); }

    public RegisterManagerRequestDTO(String email, String password, Role role, String cpf, LocalDate birthDate, Long theaterId) {
        super(email, password, role);
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.theaterId = theaterId;
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

    public Long getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(Long theaterId) {
        this.theaterId = theaterId;
    }
}
