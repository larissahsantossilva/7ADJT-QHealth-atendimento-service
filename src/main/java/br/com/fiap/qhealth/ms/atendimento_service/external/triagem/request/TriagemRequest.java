package br.com.fiap.qhealth.ms.atendimento_service.external.triagem.request;

import java.time.LocalDate;

public record TriagemRequest(
    LocalDate dataNascimento,
    TriagemAnamneseRequest anamnese
) {}