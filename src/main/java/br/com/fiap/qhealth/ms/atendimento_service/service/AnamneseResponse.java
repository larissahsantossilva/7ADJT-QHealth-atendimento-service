package br.com.fiap.qhealth.ms.atendimento_service.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AnamneseResponse {
    private UUID id;
    private boolean fumante;
    private boolean gravida;
    private boolean diabetico;
    private boolean hipertenso;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaAlteracao;
}
