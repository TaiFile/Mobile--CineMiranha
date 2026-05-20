package br.ufscar.pooa.cinema_api.features.theaters.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterTheaterRequestDTO {

    @NotBlank
    private String name;

    private String logoUrl;

    private AddressDTO address;

    public RegisterTheaterRequestDTO() {
    }

    public RegisterTheaterRequestDTO(String name, String logoUrl, AddressDTO address) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.address = address;
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
}
