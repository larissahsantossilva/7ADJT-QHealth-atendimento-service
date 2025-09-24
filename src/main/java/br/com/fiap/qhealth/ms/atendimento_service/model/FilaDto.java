package br.com.fiap.qhealth.ms.atendimento_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record FilaDto(
    UUID id,
    UUID idUnidadeSaude,
    String nomeFila,
    String tipoFila,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}