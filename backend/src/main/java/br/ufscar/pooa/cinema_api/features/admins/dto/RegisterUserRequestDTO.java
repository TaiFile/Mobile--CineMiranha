package br.ufscar.pooa.cinema_api.features.admins.dto;

import br.ufscar.pooa.cinema_api.domain.enums.Role;
import br.ufscar.pooa.cinema_api.features._shared.validation.ValueOfEnum;
import jakarta.validation.constraints.NotNull;

public class RegisterUserRequestDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @ValueOfEnum(enumClass = Role.class)
    private Role role;

    public RegisterUserRequestDTO() {
    }

    public RegisterUserRequestDTO(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
