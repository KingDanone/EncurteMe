package br.com.encurteMe.application.service;

import br.com.encurteMe.application.port.in.GetUrlUseCase;
import br.com.encurteMe.application.port.out.UrlRepositoryPort;
import br.com.encurteMe.domain.exception.UrlNotFoundException;
import br.com.encurteMe.domain.model.Url;
import org.springframework.transaction.annotation.Transactional;

public class GetUrlService implements GetUrlUseCase {

    private final UrlRepositoryPort repository;

    public GetUrlService(UrlRepositoryPort repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Url execute(String codigoEncurtado) {
        Url url = repository.findByCodigoEncurtado(codigoEncurtado)
                .orElseThrow(() -> new UrlNotFoundException(codigoEncurtado));
        Url urlAtualizada = url.incrementaClick();
        return repository.save(urlAtualizada);
    }
}
