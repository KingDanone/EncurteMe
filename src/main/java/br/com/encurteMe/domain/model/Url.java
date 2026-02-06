package br.com.encurteMe.domain.model;

import br.com.encurteMe.domain.exception.InvalidUrlException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


public class Url {
    private final UUID id;
    private final String urlOriginal;
    private final String codigoEncurtado;
    private final LocalDateTime criadoEm;
    private final long clicks;

    public Url(UUID id, String urlOriginal, String codigoEncurtado, LocalDateTime criadoEm, long clicks) {
        this.id = id;
        this.urlOriginal = validateUrl(urlOriginal);
        this.codigoEncurtado = Objects.requireNonNull(codigoEncurtado,
                "Url encurtada não pode ser null");
        this.criadoEm = criadoEm != null ? criadoEm : LocalDateTime.now();
        this.clicks = Math.max(0, clicks);
    }

    private String validateUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL não pode ser vazia");
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new InvalidUrlException("URL deve começar com http:// ou https://");
        }
        return url;
    }

    public static Url create(String urlOriginal, String codigoEncurtado) {
        return new Url(null, urlOriginal, codigoEncurtado, LocalDateTime.now(), 0);
    }

    public Url incrementaClick() {
        return new Url(id, urlOriginal, codigoEncurtado, criadoEm, clicks + 1);
    }

    public UUID getId() {
        return id;
    }

    public String getUrlOriginal() {
        return urlOriginal;
    }

    public String getCodigoEncurtado() {
        return codigoEncurtado;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public long getClicks() {
        return clicks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        boolean equals = Objects.equals(codigoEncurtado, codigoEncurtado);
        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoEncurtado);
    }
}
