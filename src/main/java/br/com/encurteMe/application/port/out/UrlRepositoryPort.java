package br.com.encurteMe.application.port.out;

import br.com.encurteMe.domain.model.Url;

import java.util.Optional;

public interface UrlRepositoryPort {
    Url save (Url url);
    Optional<Url> findByShortUrl(String codigoEncurtado);
    boolean existsByCodigoEncurtado(String codigoEncurtado);
}
