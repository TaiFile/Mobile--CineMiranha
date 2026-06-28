package br.ufscar.pooa.cinema_api.features.checkout.dto;

import java.util.List;

public class CheckoutRequestDTO {
    private Long sessionId;
    private List<String> seats;
    private int fullTickets;
    private int halfTickets;
    private float totalPrice;

    public CheckoutRequestDTO() {}

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    public List<String> getSeats() { return seats; }
    public void setSeats(List<String> seats) { this.seats = seats; }

    public int getFullTickets() { return fullTickets; }
    public void setFullTickets(int fullTickets) { this.fullTickets = fullTickets; }

    public int getHalfTickets() { return halfTickets; }
    public void setHalfTickets(int halfTickets) { this.halfTickets = halfTickets; }

    public float getTotalPrice() { return totalPrice; }
    public void setTotalPrice(float totalPrice) { this.totalPrice = totalPrice; }
}
