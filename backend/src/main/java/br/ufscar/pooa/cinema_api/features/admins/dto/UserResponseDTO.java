package br.ufscar.pooa.cinema_api.features.admins.dto;

import br.ufscar.pooa.cinema_api.domain.enums.Role;

public class UserResponseDTO {

    private Long id;
    private String email;
    private Role role;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
