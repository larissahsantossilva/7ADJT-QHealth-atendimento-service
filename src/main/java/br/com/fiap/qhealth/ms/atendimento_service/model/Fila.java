package br.com.fiap.qhealth.ms.atendimento_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fila", schema = "atendimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fila {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "unidade_saude_id", nullable = false)
    private UUID idUnidadeSaude;

    @Column(name = "nome_fila", nullable = false)
    private String nomeFila;

    @Column(name = "tipo_fila", nullable = false)
    private String tipoFila;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_ultima_alteracao")
    private LocalDateTime dataUltimaAlteracao;

}
