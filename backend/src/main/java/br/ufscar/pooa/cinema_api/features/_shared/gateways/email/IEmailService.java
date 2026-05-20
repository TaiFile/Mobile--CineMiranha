package br.ufscar.pooa.cinema_api.features._shared.gateways.email;

import java.util.Map;

public interface IEmailService {
    void sendEmail(String to, String subject, String templateName, Map<String, Object> variables);
}
