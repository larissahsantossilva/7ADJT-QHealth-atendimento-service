package br.com.fiap.qhealth.ms.atendimento_service.producer.json;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AtendimentoUbsRequestJsonTest {

    @Test
    void testRecordFields() {
        UUID id = UUID.randomUUID();
        String cpf = "12345678900";
        UUID filaId = UUID.randomUUID();
        LocalDateTime dataCriacao = LocalDateTime.now();
        LocalDateTime dataUltimaAlteracao = LocalDateTime.now();

        AtendimentoUbsRequestJson request = new AtendimentoUbsRequestJson(
            id, cpf, filaId, dataCriacao, dataUltimaAlteracao
        );

        assertEquals(id, request.id());
        assertEquals(cpf, request.cpf());
        assertEquals(filaId, request.filaId());
        assertEquals(dataCriacao, request.dataCriacao());
        assertEquals(dataUltimaAlteracao, request.dataUltimaAlteracao());
    }
}