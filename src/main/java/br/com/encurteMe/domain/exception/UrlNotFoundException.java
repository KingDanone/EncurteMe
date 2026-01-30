package br.com.encurteMe.domain.exception;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String urlEncurtada) {
        super("Url Encurtada não encontrada ");
    }
}
