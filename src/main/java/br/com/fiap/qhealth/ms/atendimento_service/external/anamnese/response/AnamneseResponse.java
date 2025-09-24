package br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record AnamneseResponse(
    UUID id,
    Boolean fumante,
    Boolean gravida,
    Boolean diabetico,
    Boolean hipertenso,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}
