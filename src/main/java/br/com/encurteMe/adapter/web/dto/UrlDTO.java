package br.com.encurteMe.adapter.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UrlDTO(

        @Schema(description = "Id interno da URL", accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        @Schema(description = "Url Original fornecida pelo usuario", example = "https://github.com/KingDanone")
        @NotBlank(message = "Url não pode está vazia")
        @Pattern(regexp = "^https?://.*", message = "Url deve começar com http:// ou https://")
        String urlOriginal,

        @Schema(description = "Codigo curto gerado pela API (até 5 caracteres)", accessMode = Schema.AccessMode.READ_ONLY, example = "abc12")
        String codigoEncurtado,

        @Schema(description = "URL encurtada completa", accessMode = Schema.AccessMode.READ_ONLY, example = "https://163.176.255.7:8080/api/abc12")
        String urlEncurtada,

        @Schema(description = "Data e horario da criação do link curto", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime criadoEm,

        @Schema(description = "Quantidade total de cliques", accessMode = Schema.AccessMode.READ_ONLY)
        Long click
) {
}
