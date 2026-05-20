package br.ufscar.pooa.cinema_api.features._shared.gateways.payment;

import br.ufscar.pooa.cinema_api.domain.enums.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentStrategyFactory {
    private final Map<PaymentMethod, IPaymentStrategy> strategyMap;

    public PaymentStrategyFactory(List<IPaymentStrategy> strategies) {
        strategyMap = strategies.stream()
                .collect(Collectors.toMap(IPaymentStrategy::getPaymentMethod, Function.identity()));
    }

    public Optional<IPaymentStrategy> getStrategy(PaymentMethod paymentMethod) {
        return Optional.ofNullable(strategyMap.get(paymentMethod));
    }
}
