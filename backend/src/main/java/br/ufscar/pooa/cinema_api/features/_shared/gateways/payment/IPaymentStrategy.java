package br.ufscar.pooa.cinema_api.features._shared.gateways.payment;

import br.ufscar.pooa.cinema_api.domain.enums.PaymentMethod;

public interface IPaymentStrategy {
    boolean pay(Integer amount);
    PaymentMethod getPaymentMethod();
}
