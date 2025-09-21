package br.com.fiap.qhealth.ms.atendimento_service.service;

import java.util.UUID;

public record TriagemAnamneseRequest(
    UUID id,
    boolean fumante,
    boolean gravida,
    boolean diabetico,
    boolean hipertenso
) {}