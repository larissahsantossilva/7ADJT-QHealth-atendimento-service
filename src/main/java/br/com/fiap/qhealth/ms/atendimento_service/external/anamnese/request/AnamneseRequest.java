package br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.request;

public record AnamneseRequest(
    Boolean fumante,
    Boolean gravida,
    Boolean diabetico,
    Boolean hipertenso)
{}
