package br.ufscar.pooa.cinema_api.features.theaters.dto;

import br.ufscar.pooa.cinema_api.features.admins.dto.UserResponseDTO;

import java.util.List;

public class TheaterResponseDTO {

    private Long id;
    private String name;
    private String logoUrl;
    private AddressDTO address;
    private List<UserResponseDTO> managers;
    private Double distance;

    public TheaterResponseDTO() {
    }

    public TheaterResponseDTO(Long id, String name, String logoUrl, AddressDTO address, List<UserResponseDTO> managers) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
        this.address = address;
        this.managers = managers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<UserResponseDTO> getManagers() {
        return managers;
    }

    public void setManagers(List<UserResponseDTO> managers) {
        this.managers = managers;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
