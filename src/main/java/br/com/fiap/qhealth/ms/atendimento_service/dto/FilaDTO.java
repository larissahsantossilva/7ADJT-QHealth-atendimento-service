package br.com.fiap.qhealth.ms.atendimento_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class FilaDTO {
    private UUID id;
    private UUID idUnidadeSaude;
    private String nomeFila;
    private String tipoFila;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaAlteracao;
}
