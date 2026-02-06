package br.com.encurteMe.adapter.web.mapper;

import br.com.encurteMe.adapter.web.dto.UrlDTO;
import br.com.encurteMe.domain.model.Url;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlWebMapper {
//pego o Url padrão e retrna um UrlDTO
    default UrlDTO toResponse(Url url){
        return new UrlDTO(
                url.getId(),
                url.getUrlOriginal(),
                url.getCodigoEncurtado(),
                "http://localhost:8080/api/" + url.getCodigoEncurtado(),
                url.getCriadoEm(),
                url.getClicks()
        );
    }
}
