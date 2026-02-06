package br.com.encurteMe.adapter.web.controller;


import br.com.encurteMe.adapter.web.dto.UrlDTO;
import br.com.encurteMe.adapter.web.mapper.UrlWebMapper;
import br.com.encurteMe.application.port.in.CreateShortUrlCase;
import br.com.encurteMe.application.port.in.DeleteAllUseCase;
import br.com.encurteMe.application.port.in.GetUrlUseCase;
import br.com.encurteMe.application.port.in.GetAllUrlUseCase;
import br.com.encurteMe.domain.model.Url;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "URL Shortener", description = "API para gerar URL curta")
public class UrlController {

    private final CreateShortUrlCase createUseCase;
    private final GetUrlUseCase getUrlUseCase;
    private final DeleteAllUseCase deleteAllUseCase;
    private final GetAllUrlUseCase getAllUrlUseCase;
    private final UrlWebMapper mapper;

    public UrlController(CreateShortUrlCase createUseCase,
                         GetUrlUseCase getUrlUseCase,
                         DeleteAllUseCase deleteAllUseCase,
                         UrlWebMapper mapper,
                         GetAllUrlUseCase getAllUrlUseCase
    ) {
        this.createUseCase = createUseCase;
        this.getUrlUseCase = getUrlUseCase;
        this.deleteAllUseCase = deleteAllUseCase;
        this.getAllUrlUseCase = getAllUrlUseCase;
        this.mapper = mapper;
    }

    @PostMapping("/shortener")
    @Operation(summary = "Criar URL encurtada",
            description = "Recebe uma URL e retorna o código encurtado")
    public ResponseEntity<UrlDTO> shorten(@Valid @RequestBody UrlDTO request) {
        var codigoCurto = createUseCase.execute(request.urlOriginal());
        Url url = getUrlUseCase.execute(codigoCurto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toResponse(url));
    }

    @GetMapping("/{codigoEncurtado}")
    @Operation(summary = "Redireciona o usuario para o link original",
            description = "Recebe o codigo encurtado e redireciona o usuario para a URL original")
    public ResponseEntity<Void> redirect(@PathVariable String codigoEncurtado) {
        Url url = getUrlUseCase.execute(codigoEncurtado);
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(url.getUrlOriginal()))
                .build();
    }

    @DeleteMapping
    @Operation(summary = "Deleta todas as URLs",
            description = "Deleta todas as URLs salvas")
    public ResponseEntity<Void> deleteAll() {
        deleteAllUseCase.execute();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    @Operation(summary = "Listar URLs", description = "Retorna lista paginada")
    public ResponseEntity<List<UrlDTO>> listAll(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size
    ) {
        var urls = getAllUrlUseCase.execute(page, size);
        var response = urls.stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
