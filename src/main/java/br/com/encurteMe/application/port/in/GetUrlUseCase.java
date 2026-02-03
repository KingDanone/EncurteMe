package br.com.encurteMe.application.port.in;
import br.com.encurteMe.domain.model.Url;


public interface GetUrlUseCase {
    Url execute(String codigoEncurtado);
}
