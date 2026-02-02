package br.com.encurteMe.domain.exception;

public class UrlNotFoundException extends RuntimeException {
    private final String codigoEncurtado;

    public UrlNotFoundException(String codigoEncurtado) {
        super("Url Encurtada não encontrada: " + codigoEncurtado);
        this.codigoEncurtado = codigoEncurtado;
    }

    public String getCodigoEncurtado(){
        return codigoEncurtado;
    }
}