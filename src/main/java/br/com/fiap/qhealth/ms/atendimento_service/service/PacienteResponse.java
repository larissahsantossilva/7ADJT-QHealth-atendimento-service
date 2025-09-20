package br.com.fiap.qhealth.ms.atendimento_service.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PacienteResponse {
    private UUID id;
    private String nome;
    private String email;
    private String login;
    private String cpf;
    private String genero;
    private String telefone;
    private LocalDate dataNascimento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaAlteracao;
}
