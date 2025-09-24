package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.external.ubs.UbsClient;
import br.com.fiap.qhealth.ms.atendimento_service.external.ubs.response.EnderecoResponse;
import br.com.fiap.qhealth.ms.atendimento_service.external.ubs.response.UnidadeSaudeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UbsServiceTest {

    private UbsClient ubsClient;
    private UbsService ubsService;

    @BeforeEach
    void setUp() {
        ubsClient = mock(UbsClient.class);
        ubsService = new UbsService(ubsClient);
    }

    @Test
    void testListarUbs() {
        EnderecoResponse enderecoResponse = new EnderecoResponse(UUID.randomUUID(), "Rua Teste", 123, "02201123", "Apto 10", "Bairro Teste", "Cidade Teste", LocalDateTime.now(), LocalDateTime.now());
        UnidadeSaudeResponse ubs = new UnidadeSaudeResponse(UUID.randomUUID(), "UBS Teste", enderecoResponse, LocalDateTime.now(), LocalDateTime.now());
        List<UnidadeSaudeResponse> ubsList = List.of(ubs);
        ResponseEntity<List<UnidadeSaudeResponse>> entity = ResponseEntity.ok(ubsList);

        when(ubsClient.listarUbs(0, 10)).thenReturn(entity);

        ResponseEntity<List<UnidadeSaudeResponse>> result = ubsService.listarUbs();

        assertEquals(entity, result);
        assertEquals(ubsList, result.getBody());
    }
}
