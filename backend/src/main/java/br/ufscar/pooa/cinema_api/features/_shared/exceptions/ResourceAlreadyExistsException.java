package br.ufscar.pooa.cinema_api.features._shared.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String entity, String key, String value) {
        super(String.format("%s already exists with %s %s.", entity, key, value));
    }
}
