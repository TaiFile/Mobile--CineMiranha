package br.ufscar.pooa.cinema_api.features.checkout.controller;

import br.ufscar.pooa.cinema_api.features.checkout.dto.CheckoutRequestDTO;
import br.ufscar.pooa.cinema_api.features.checkout.dto.CheckoutResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @PostMapping
    public ResponseEntity<CheckoutResponseDTO> checkout(@RequestBody CheckoutRequestDTO request) {
        String orderId = UUID.randomUUID().toString();
        return ResponseEntity.ok(new CheckoutResponseDTO("ok", orderId));
    }
}
