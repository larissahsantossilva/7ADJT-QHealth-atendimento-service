package br.com.fiap.qhealth.ms.atendimento_service.external.ubs.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record EnderecoResponse(
    UUID id,
    String rua,
    Integer numero,
    String cep,
    String complemento,
    String bairro,
    String cidade,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}
