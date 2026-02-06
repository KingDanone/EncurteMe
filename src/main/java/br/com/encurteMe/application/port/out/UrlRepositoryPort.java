package br.com.encurteMe.application.port.out;

import br.com.encurteMe.domain.model.Url;

import java.util.List;
import java.util.Optional;

public interface UrlRepositoryPort {
    Url save (Url url);
    Optional<Url> findByCodigoEncurtado(String codigoEncurtado);
    boolean existsByCodigoEncurtado(String codigoEncurtado);
    void deleteAll();
    List<Url> findAll(int page, int size);
}
