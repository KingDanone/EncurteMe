package br.com.encurteMe.adapter.persistence.repository;

import br.com.encurteMe.adapter.persistence.entities.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUrlRepository extends JpaRepository<UrlEntity, UUID> {
    Optional<UrlEntity> findByCodigoEncurtado(String codigoCurto);
    boolean existsByCodigoEncurtado(String codigoEncurtado);
}
