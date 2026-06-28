package br.ufscar.pooa.cinema_api.features.checkout.dto;

public class CheckoutResponseDTO {
    private String status;
    private String orderId;

    public CheckoutResponseDTO(String status, String orderId) {
        this.status = status;
        this.orderId = orderId;
    }

    public String getStatus() { return status; }
    public String getOrderId() { return orderId; }
}
