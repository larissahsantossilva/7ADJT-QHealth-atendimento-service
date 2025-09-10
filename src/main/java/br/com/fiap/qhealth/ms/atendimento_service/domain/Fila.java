package br.com.fiap.qhealth.ms.atendimento_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public class Fila {
    private UUID id;
    private UUID idUnidadeSaude;
    private String nomeFila;
    private String tipoFila;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaAlteracao;
}
