package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.TriagemClient;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.request.TriagemAnamneseRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.request.TriagemRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.response.TriagemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TriagemServiceTest {

    private TriagemClient triagemClient;
    private TriagemService triagemService;

    @BeforeEach
    void setUp() {
        triagemClient = mock(TriagemClient.class);
        triagemService = new TriagemService(triagemClient);
    }

    @Test
    void testDefinirTriagem() {
        TriagemAnamneseRequest triagemAnamneseRequest = new TriagemAnamneseRequest(UUID.randomUUID(), false, true, false, true);
        TriagemRequest request = new TriagemRequest(LocalDate.now(), triagemAnamneseRequest);
        TriagemResponse response = new TriagemResponse(false);
        ResponseEntity<TriagemResponse> entity = ResponseEntity.ok(response);

        when(triagemClient.definirTriagem(request)).thenReturn(entity);

        ResponseEntity<TriagemResponse> result = triagemService.definirTriagem(request);

        assertEquals(entity, result);
        assertEquals(response, result.getBody());
    }
}