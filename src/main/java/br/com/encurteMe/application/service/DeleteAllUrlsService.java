package br.com.encurteMe.application.service;

import br.com.encurteMe.application.port.in.DeleteAllUseCase;
import br.com.encurteMe.application.port.out.UrlRepositoryPort;

public class DeleteAllUrlsService implements DeleteAllUseCase {

    private final UrlRepositoryPort repository;

    public DeleteAllUrlsService(UrlRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.deleteAll();
    }
}
