package br.ufscar.pooa.cinema_api.infrastructure.providers.payment;


import br.ufscar.pooa.cinema_api.features._shared.gateways.payment.IPaymentStrategy;
import br.ufscar.pooa.cinema_api.domain.enums.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PixPaymentMethodStrategy implements IPaymentStrategy {

    @Override
    public boolean pay(Integer amount) {
        System.out.println("Pagamento de R$ " + amount + " realizado com PIX.");
        return true;
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.PIX;
    }
}
