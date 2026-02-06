package br.com.encurteMe.adapter.persistence.repository;

import br.com.encurteMe.adapter.persistence.entities.UrlEntity;
import br.com.encurteMe.adapter.persistence.mapper.UrlEntityMapper;
import br.com.encurteMe.application.port.out.UrlRepositoryPort;
import br.com.encurteMe.domain.model.Url;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
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
    public Optional<Url> findByCodigoEncurtado(String codigoEncurtado) {
        return repository.findByCodigoEncurtado(codigoEncurtado)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByCodigoEncurtado(String codigoEncurtado) {
        return repository.existsByCodigoEncurtado(codigoEncurtado);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<Url> findAll(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return repository.findAll(pageable)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
