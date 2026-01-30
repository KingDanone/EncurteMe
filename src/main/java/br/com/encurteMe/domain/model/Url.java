package br.com.encurteMe.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Url {
    private final Long id;
    private final String urlOriginal;
    private final String urlEncurtada;
    private final LocalDateTime criadoEm;
    private final long clicks;

    public Url(Long id, String urlOriginal, String urlEncurtada, LocalDateTime criadoEm, long clicks) {
        this.id = id;
        this.urlOriginal = validateUrl(urlOriginal);
        this.urlEncurtada = Objects.requireNonNull(urlEncurtada, "Url encurtada não pode ser null");
        this.criadoEm = criadoEm != null ? criadoEm : LocalDateTime.now();
        this.clicks = Math.max(0, clicks);
    }

    private String validateUrl(String url) {
        if (url == null || url.isBlank()){
            throw new IllegalArgumentException("Url não pode ser vazia");
        }
        return url;
    }

    public static Url create(String urlOriginal, String urlEncurtada) {
        return new Url(null, urlOriginal, urlEncurtada, LocalDateTime.now(), 0);
    }

    public Url incrementaClick(){
        return new Url(id, urlOriginal, urlEncurtada, criadoEm, clicks+1);
    }

    public Long getId() {
        return id;
    }

    public String getUrlOriginal() {
        return urlOriginal;
    }

    public String getUrlEncurtada() {
        return urlEncurtada;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public long getClicks() {
        return clicks;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        return clicks == url.clicks && Objects.equals(id, url.id) && Objects.equals(urlOriginal, url.urlOriginal) && Objects.equals(urlEncurtada, url.urlEncurtada) && Objects.equals(criadoEm, url.criadoEm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, urlOriginal, urlEncurtada, criadoEm, clicks);
    }
}
