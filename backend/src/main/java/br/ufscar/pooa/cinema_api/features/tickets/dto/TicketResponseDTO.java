package br.ufscar.pooa.cinema_api.features.tickets.dto;
import br.ufscar.pooa.cinema_api.domain.enums.PaymentMethod;

import java.time.Instant;
import java.util.Objects;

public class TicketResponseDTO {
    private Long id;
    private Instant paymentDate;
    private Integer priceInCents;
    private PaymentMethod paymentMethod;
    private Long sessionId;
    private Long clientId;
    private Long seatId;

    public TicketResponseDTO(Instant paymentDate, Long id, Integer priceInCents, PaymentMethod paymentMethod) {
        this.paymentDate = paymentDate;
        this.id = id;
        this.priceInCents = priceInCents;
        this.paymentMethod = paymentMethod;
    }

    public TicketResponseDTO() {
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriceInCents() {
        return priceInCents;
    }

    public void setPriceInCents(Integer priceInCents) {
        this.priceInCents = priceInCents;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    @Override
    public String toString() {
        return "TicketResponseDTO{" +
                "id=" + id +
                ", paymentDate=" + paymentDate +
                ", priceInCents=" + priceInCents +
                ", paymentMethod=" + paymentMethod +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TicketResponseDTO that = (TicketResponseDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getPaymentDate(), that.getPaymentDate()) && Objects.equals(getPriceInCents(), that.getPriceInCents()) && getPaymentMethod() == that.getPaymentMethod();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPaymentDate(), getPriceInCents(), getPaymentMethod());
    }
}
