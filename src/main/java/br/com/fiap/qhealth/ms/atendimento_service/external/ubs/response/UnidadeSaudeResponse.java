package br.com.fiap.qhealth.ms.atendimento_service.external.ubs.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UnidadeSaudeResponse(
    UUID id,
    String nome,
    EnderecoResponse endereco,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}
