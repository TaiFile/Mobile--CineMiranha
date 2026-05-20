package br.ufscar.pooa.cinema_api.features._shared.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String entity, String key, String value) {
        super(String.format("%s not found with %s %s.", entity, key, value));
    }
}
