package br.com.encurteMe.domain.exception;

public class ShortCodeGenerationException extends RuntimeException {

    public ShortCodeGenerationException(int maxTentativas) {
        super("Falha ao gerar codigo unico apos " + maxTentativas + " tentativas");
    }
}
