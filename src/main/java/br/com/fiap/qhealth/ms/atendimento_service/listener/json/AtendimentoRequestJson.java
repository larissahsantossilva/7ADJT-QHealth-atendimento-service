package br.com.fiap.qhealth.ms.atendimento_service.listener.json;

import java.time.LocalDateTime;
import java.util.UUID;

public record AtendimentoRequestJson(
    UUID id,
    UUID idPaciente,
    Integer posicaoFila,
    UUID idAnamnese,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao)
{}

