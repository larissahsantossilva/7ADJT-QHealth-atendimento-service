package br.com.fiap.qhealth.ms.atendimento_service.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnamneseRequest {
    private boolean fumante;
    private boolean gravida;
    private boolean diabetico;
    private boolean hipertenso;
}
