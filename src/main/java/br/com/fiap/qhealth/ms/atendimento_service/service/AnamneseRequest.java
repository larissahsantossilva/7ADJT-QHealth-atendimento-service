package br.com.fiap.qhealth.ms.atendimento_service.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record AnamneseRequest(
    Boolean fumante,
    Boolean gravida,
    Boolean diabetico,
    Boolean hipertenso)
{}
