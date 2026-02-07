package br.com.encurteMe.adapter.web.mapper;

import br.com.encurteMe.adapter.web.dto.UrlDTO;
import br.com.encurteMe.domain.model.Url;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Value;

@Mapper(componentModel = "spring")
public abstract class UrlWebMapper {

    @Value("${app.base-url}")
    protected String baseUrl;

    public UrlDTO toResponse(Url url) {
        return new UrlDTO(
                url.getId(),
                url.getUrlOriginal(),
                url.getCodigoEncurtado(),
                baseUrl + "/api/" + url.getCodigoEncurtado(),
                url.getCriadoEm(),
                url.getClicks()
        );
    }
}
