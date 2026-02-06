package br.com.encurteMe.config;

import br.com.encurteMe.application.port.in.CreateShortUrlCase;
import br.com.encurteMe.application.port.in.DeleteAllUseCase;
import br.com.encurteMe.application.port.in.GetUrlUseCase;
import br.com.encurteMe.application.port.in.GetAllUrlUseCase;
import br.com.encurteMe.application.port.out.UrlRepositoryPort;
import br.com.encurteMe.application.service.CreateShortUrlService;
import br.com.encurteMe.application.service.DeleteAllUrlsService;
import br.com.encurteMe.application.service.GetUrlService;
import br.com.encurteMe.application.service.GetAllUrlUrlsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CreateShortUrlCase createShortUrlCase(UrlRepositoryPort repository) {
        return new CreateShortUrlService(repository);
    }

    @Bean
    public DeleteAllUseCase deleteAllUseCase(UrlRepositoryPort repository) {
        return new DeleteAllUrlsService(repository);
    }

    @Bean
    public GetUrlUseCase getUrlUseCase(UrlRepositoryPort repository) {
        return new GetUrlService(repository);
    }

    @Bean
    public GetAllUrlUseCase listAllUseCase(UrlRepositoryPort repository) {
        return new GetAllUrlUrlsService(repository);
    }
}
