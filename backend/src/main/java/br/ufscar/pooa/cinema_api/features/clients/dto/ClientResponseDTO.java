package br.ufscar.pooa.cinema_api.features.clients.dto;

import br.ufscar.pooa.cinema_api.features.tickets.dto.TicketResponseDTO;
import br.ufscar.pooa.cinema_api.features.admins.dto.UserResponseDTO;
import br.ufscar.pooa.cinema_api.domain.enums.Gender;
import br.ufscar.pooa.cinema_api.domain.enums.Role;

import java.time.LocalDate;
import java.util.List;

public class ClientResponseDTO extends UserResponseDTO {

    private String cpf;
    private String name;
    private String phoneNumber;
    private Gender gender;
    private LocalDate birthDate;
    private List<TicketResponseDTO> tickets;

    public ClientResponseDTO() {
        super();
    }

    public ClientResponseDTO(Long id, String name, String phoneNumber, String email, String password, Role role, String cpf, Gender gender, LocalDate birthDate, List<TicketResponseDTO> tickets) {
        super(id, email, password, role);
        this.cpf = cpf;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
        this.tickets = tickets;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<TicketResponseDTO> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketResponseDTO> tickets) {
        this.tickets = tickets;
    }
}
