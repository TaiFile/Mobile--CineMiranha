package br.ufscar.pooa.cinema_api.features.tickets.dto;

import br.ufscar.pooa.cinema_api.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

public class RegisterTicketRequestDTO {
    @NotNull
    private Long sessionId;

    @NotNull
    private Long seatId;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private Long clientId;

    @NotNull
    private Integer priceInCentsClient;

    public RegisterTicketRequestDTO(Long sessionId, Long seatId,
                                    PaymentMethod paymentMethod,
                                    Long clientId,
                                    Integer priceInCentsClient) {
        this.sessionId = sessionId;
        this.seatId = seatId;
        this.paymentMethod = paymentMethod;
        this.clientId = clientId;
        this.priceInCentsClient = priceInCentsClient;
    }

    public RegisterTicketRequestDTO() {
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Integer getPriceInCentsClient() {
        return priceInCentsClient;
    }

    public void setPriceInCentsClient(Integer priceInCents) {
        this.priceInCentsClient = priceInCents;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    @Override
    public String toString() {
        return "RegisterTicketRequestDTO{" +
                "sessionId=" + sessionId +
                ", seatId=" + seatId +
                ", paymentMethod=" + paymentMethod +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RegisterTicketRequestDTO that = (RegisterTicketRequestDTO) o;
        return Objects.equals(getSessionId(), that.getSessionId()) &&
                Objects.equals(getSeatId(), that.getSeatId()) &&
                getPaymentMethod() == that.getPaymentMethod();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSessionId(), getSeatId(), getPaymentMethod());
    }
}
