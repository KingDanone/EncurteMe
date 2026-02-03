package br.com.encurteMe.adapter.persistence.repository;

import br.com.encurteMe.adapter.persistence.entities.UrlEntity;
import br.com.encurteMe.adapter.persistence.mapper.UrlEntityMapper;
import br.com.encurteMe.application.port.out.UrlRepositoryPort;
import br.com.encurteMe.domain.model.Url;

import java.util.Optional;

public class JpaUrlRepository implements UrlRepositoryPort {

    private final SpringDataUrlRepository repository;
    private final UrlEntityMapper mapper;

    public JpaUrlRepository(SpringDataUrlRepository repository, UrlEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Url save(Url url) {
        UrlEntity urlEntity = mapper.toEntity(url);
        UrlEntity saved = repository.save(urlEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Url> findByShortUrl(String codigoEncurtado) {
        return repository.findByShortUrl(codigoEncurtado)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByCodigoEncurtado(String codigoEncurtado) {
        return repository.existesByCodigoEncurtado(codigoEncurtado);
    }
}
