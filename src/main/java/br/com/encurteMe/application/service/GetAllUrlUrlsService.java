package br.com.encurteMe.application.service;

import br.com.encurteMe.application.port.in.GetAllUrlUseCase;
import br.com.encurteMe.application.port.out.UrlRepositoryPort;
import br.com.encurteMe.domain.model.Url;

import java.util.List;

public class GetAllUrlUrlsService implements GetAllUrlUseCase {

    private final UrlRepositoryPort repository;

    public GetAllUrlUrlsService(UrlRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Url> execute(int page, int size) {
        return repository.findAll(page, size);
    }
}
