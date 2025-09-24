package br.com.fiap.qhealth.ms.atendimento_service.external.paciente.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PacienteResponse(
    UUID id,
    String nome,
    String email,
    String login,
    String cpf,
    String genero,
    String telefone,
    LocalDate dataNascimento,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}