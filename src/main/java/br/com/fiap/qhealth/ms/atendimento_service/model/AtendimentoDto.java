package br.com.fiap.qhealth.ms.atendimento_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record AtendimentoDto(
    UUID id,
    String cpf,
    UUID idAnamnese,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}