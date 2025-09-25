package br.com.fiap.qhealth.ms.atendimento_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AtendimentoDto(
    UUID id,
    String cpf,
    UUID anamneseId,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}