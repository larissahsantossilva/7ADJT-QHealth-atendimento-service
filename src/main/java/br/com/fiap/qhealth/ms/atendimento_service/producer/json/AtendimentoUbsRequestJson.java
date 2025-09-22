package br.com.fiap.qhealth.ms.atendimento_service.producer.json;

import java.time.LocalDateTime;
import java.util.UUID;

public record AtendimentoUbsRequestJson(
    UUID id,
    UUID pacienteId,
    UUID filaId,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}
