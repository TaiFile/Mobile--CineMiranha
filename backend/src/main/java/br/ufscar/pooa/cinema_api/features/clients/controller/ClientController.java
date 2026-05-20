package br.ufscar.pooa.cinema_api.features.clients.controller;

import br.ufscar.pooa.cinema_api.features.clients.dto.ClientResponseDTO;
import br.ufscar.pooa.cinema_api.features.clients.dto.RegisterClientRequestDTO;
import br.ufscar.pooa.cinema_api.features.clients.usecase.IRegisterClientUseCase;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final IRegisterClientUseCase registerClientUseCase;

    public ClientController(
        IRegisterClientUseCase registerClientUseCase) {
        this.registerClientUseCase = registerClientUseCase;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> register(
        @RequestBody RegisterClientRequestDTO registerRequestBody) {
        var responseDTO = registerClientUseCase.execute(registerRequestBody);

        URI uri = URI.create("/clients/" + responseDTO.getId());

        return ResponseEntity.created(uri).body(responseDTO);
    }
}
