package br.com.encurteMe.application.port.in;

import br.com.encurteMe.domain.model.Url;

import java.util.List;

public interface GetAllUrlUseCase {
    List<Url> execute(int page, int size);
}
