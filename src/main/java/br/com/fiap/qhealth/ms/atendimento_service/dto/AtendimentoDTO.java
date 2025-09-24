package br.com.fiap.qhealth.ms.atendimento_service.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class AtendimentoDTO {
    private UUID id;
    private String cpf;
    private UUID idFila;
    private UUID idAnamnese;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaAlteracao;
}
