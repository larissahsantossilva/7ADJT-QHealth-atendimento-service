package br.com.fiap.qhealth.ms.atendimento_service.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TriagemRequest {
    private LocalDate dataNascimento;
    private TriagemAnamneseRequest anamnese;
}
