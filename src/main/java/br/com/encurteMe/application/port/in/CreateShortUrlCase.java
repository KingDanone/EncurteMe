package br.com.encurteMe.application.port.in;

import br.com.encurteMe.domain.model.Url;

public interface CreateShortUrlCase {
    Url execute(String urlOriginal);
}
