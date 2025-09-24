package br.com.fiap.qhealth.ms.atendimento_service.listener.json;

import java.time.LocalDateTime;
import java.util.UUID;

public record AtendimentoRequestJson(
    UUID id,
    String cpf,
    Boolean fumante,
    Boolean gravida,
    Boolean diabetico,
    Boolean hipertenso,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}

