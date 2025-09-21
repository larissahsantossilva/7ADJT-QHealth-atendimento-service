package br.com.fiap.qhealth.ms.atendimento_service.service;

import java.time.LocalDate;

public record TriagemRequest(
    LocalDate dataNascimento,
    TriagemAnamneseRequest anamnese
) {}