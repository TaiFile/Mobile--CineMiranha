package br.ufscar.pooa.cinema_api.features.managers.controller;

import br.ufscar.pooa.cinema_api.features.managers.dto.ManagerResponseDTO;
import br.ufscar.pooa.cinema_api.features.managers.dto.RegisterManagerRequestDTO;
import br.ufscar.pooa.cinema_api.features.managers.usecase.IRegisterManagerUseCase;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    private final IRegisterManagerUseCase registerManagerUseCase;

    public ManagerController(IRegisterManagerUseCase registerManagerUseCase) {
        this.registerManagerUseCase = registerManagerUseCase;
    }

    @PostMapping
    public ResponseEntity<ManagerResponseDTO> register(
        @RequestBody RegisterManagerRequestDTO registerRequestBody) {
        var responseDTO = registerManagerUseCase.execute(registerRequestBody);

        URI uri = URI.create("/managers/" + responseDTO.getId());

        return ResponseEntity.created(uri).body(responseDTO);
    }
}
