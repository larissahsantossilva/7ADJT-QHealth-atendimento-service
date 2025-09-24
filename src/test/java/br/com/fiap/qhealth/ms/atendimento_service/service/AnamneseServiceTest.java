package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.AnamneseClient;
import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.request.AnamneseRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.response.AnamneseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnamneseServiceTest {

    private AnamneseClient anamneseClient;
    private AnamneseService anamneseService;

    @BeforeEach
    void setUp() {
        anamneseClient = mock(AnamneseClient.class);
        anamneseService = new AnamneseService(anamneseClient);
    }

    @Test
    void testListarAnamneses() {
        LocalDateTime now = LocalDateTime.now();
        AnamneseResponse anamneseResponse = new AnamneseResponse(UUID.fromString("f778903b-adfd-4083-9f9a-f4bd83"), false, true, false, true, now, now);
        List<AnamneseResponse> mockList = List.of(anamneseResponse, anamneseResponse);
        ResponseEntity<List<AnamneseResponse>> mockResponse = ResponseEntity.ok(mockList);

        when(anamneseClient.listarAnamneses(0, 10)).thenReturn(mockResponse);

        ResponseEntity<List<AnamneseResponse>> response = anamneseService.listarAnamneses();

        assertEquals(mockResponse, response);
        assertEquals(2, response.getBody().size());
        verify(anamneseClient, times(1)).listarAnamneses(0, 10);
    }

    @Test
    void testCriarAnamnese() {
        AnamneseRequest request = new AnamneseRequest(false, true, false, true);
        UUID mockId = UUID.randomUUID();
        ResponseEntity<UUID> mockResponse = ResponseEntity.ok(mockId);

        when(anamneseClient.criarAnamnese(request)).thenReturn(mockResponse);

        ResponseEntity<UUID> response = anamneseService.criarAnamnese(request);

        assertEquals(mockResponse, response);
        assertEquals(mockId, response.getBody());
        verify(anamneseClient, times(1)).criarAnamnese(request);
    }
}