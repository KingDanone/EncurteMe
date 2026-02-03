package br.com.encurteMe.adapter.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "urls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "url_original", nullable = false)
    private String urlOriginal;

    @Column(name = "codigo_encurtado", nullable = false, unique = true)
    private String codigoEncurtado;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private long clicks;
}