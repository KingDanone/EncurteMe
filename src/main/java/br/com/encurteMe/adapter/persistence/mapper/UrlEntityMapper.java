package br.com.encurteMe.adapter.persistence.mapper;

import br.com.encurteMe.adapter.persistence.entities.UrlEntity;
import br.com.encurteMe.domain.model.Url;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlEntityMapper {

    Url toDomain(UrlEntity entity);

    UrlEntity toEntity(Url domain);
}
