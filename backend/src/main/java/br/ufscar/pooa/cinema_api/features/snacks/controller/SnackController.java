package br.ufscar.pooa.cinema_api.features.snacks.controller;

import br.ufscar.pooa.cinema_api.features.snacks.dto.SnackResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/snacks")
public class SnackController {

    @GetMapping
    public ResponseEntity<List<SnackResponseDTO>> getSnacks() {
        List<SnackResponseDTO> snacks = List.of(
            new SnackResponseDTO(1, "Pipoca Salgada P", "250g de pipoca salgada", 19.99f, "popcorn"),
            new SnackResponseDTO(2, "Pipoca Salgada M", "400g de pipoca salgada", 29.99f, "popcorn"),
            new SnackResponseDTO(3, "Balde de pipoca salgada", "500g de pipoca salgada", 59.99f, "popcorn"),
            new SnackResponseDTO(4, "Refrigerante 500ml", "Coca-cola, Fanta ou Sprite", 12.00f, "drinks"),
            new SnackResponseDTO(5, "Água Mineral 500ml", "Com ou sem gás", 6.00f, "drinks"),
            new SnackResponseDTO(6, "M&Ms", "Pacote 80g", 15.00f, "sweets"),
            new SnackResponseDTO(7, "Combo Pipoca + Refri", "Pipoca M + Refri 500ml", 38.00f, "combos")
        );
        return ResponseEntity.ok(snacks);
    }
}
