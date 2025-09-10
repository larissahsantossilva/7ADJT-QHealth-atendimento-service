package br.com.fiap.qhealth.ms.atendimento_service.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Atendimento {
    private UUID id;
    private UUID idPaciente;
    private UUID idFila;
    private Integer posicaoFila;
    private UUID idAnamnese;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaAlteracao;
}
