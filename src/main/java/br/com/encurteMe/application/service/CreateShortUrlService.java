package br.com.encurteMe.application.service;

import br.com.encurteMe.application.port.in.CreateShortUrlCase;
import br.com.encurteMe.application.port.out.UrlRepositoryPort;
import br.com.encurteMe.domain.exception.ShortCodeGenerationException;
import br.com.encurteMe.domain.model.Url;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;

public class CreateShortUrlService implements CreateShortUrlCase {

    private final UrlRepositoryPort repository;

    public CreateShortUrlService(UrlRepositoryPort repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Url execute(String urlOriginal) {
        String codigoCurto = gerarCodigoUnico();
        Url url = Url.create(urlOriginal, codigoCurto);
        return repository.save(url);
    }

    private String gerarCodigoUnico() {
        int maxTentativas = 10;

        for (int i = 0; i < maxTentativas; i++) {
            String candidato = RandomStringUtils
                    .randomAlphanumeric(5)
                    .toLowerCase();
            if (!repository.existsByCodigoEncurtado(candidato)) {
                return candidato;
            }
        }

        throw new ShortCodeGenerationException(maxTentativas);
    }
}
