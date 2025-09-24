package br.com.fiap.qhealth.ms.atendimento_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record FilaDto(
    UUID id,
    UUID unidadeSaudeId,
    String nomeFila,
    String tipoFila,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}