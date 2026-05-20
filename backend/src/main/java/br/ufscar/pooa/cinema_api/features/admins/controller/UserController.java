package br.ufscar.pooa.cinema_api.features.admins.controller;

import br.ufscar.pooa.cinema_api.features.admins.dto.RegisterUserRequestDTO;
import br.ufscar.pooa.cinema_api.features.admins.dto.UserResponseDTO;
import br.ufscar.pooa.cinema_api.features.admins.usecase.IRegisterUserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IRegisterUserUseCase registerUserUseCase;

    public UserController(IRegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterUserRequestDTO registerRequestBody) {
        var responseDTO = registerUserUseCase.execute(registerRequestBody);

        URI uri = URI.create("/users/" + responseDTO.getId());

        return ResponseEntity.created(uri).body(responseDTO);
    }
}
