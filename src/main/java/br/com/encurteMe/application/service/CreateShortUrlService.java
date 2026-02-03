package br.com.encurteMe.application.service;

import br.com.encurteMe.application.port.in.CreateShortUrlCase;
import br.com.encurteMe.application.port.out.UrlRepositoryPort;
import br.com.encurteMe.domain.model.Url;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class CreateShortUrlService implements CreateShortUrlCase {

    private final UrlRepositoryPort repository;

    public CreateShortUrlService(UrlRepositoryPort repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public String execute(String urlOriginal) {
        String codigoCurto = gerarCodigoUnico();
        Url url = Url.create(urlOriginal, codigoCurto);
        repository.save(url);
        return codigoCurto;
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

        throw new RuntimeException(
                "Falha ao gerar código unico de pois de "
                        + maxTentativas + " tentativas"
        );
    }
}
