package br.com.encurteMe.application.service;

import br.com.encurteMe.application.port.in.GetUrlUseCase;
import br.com.encurteMe.application.port.out.UrlRepositoryPort;
import br.com.encurteMe.domain.exception.InvalidUrlException;
import br.com.encurteMe.domain.exception.UrlNotFoundException;
import br.com.encurteMe.domain.model.Url;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetUrlService implements GetUrlUseCase {

    private final UrlRepositoryPort repository;

    public GetUrlService(UrlRepositoryPort repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Url execute(String codigoEncurtado) {
        Url url = repository.findByShortUrl(codigoEncurtado)
                .orElseThrow(() -> new UrlNotFoundException(codigoEncurtado));
        Url urlAtualizada = url.incrementaClick();
        return repository.save(urlAtualizada);
    }
}
