package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.external.ubs.response.EnderecoResponse;
import br.com.fiap.qhealth.ms.atendimento_service.external.ubs.response.UnidadeSaudeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class InicializacaoServiceTest {

    private FilaService filaService;
    private UbsService ubsService;
    private InicializacaoService inicializacaoService;

    @BeforeEach
    void setUp() {
        filaService = mock(FilaService.class);
        ubsService = mock(UbsService.class);
        inicializacaoService = new InicializacaoService(filaService, ubsService);
    }

    @Test
    void testRunCreatesFilasForUbs() {
        EnderecoResponse enderecoResponse = new EnderecoResponse(UUID.randomUUID(), "Rua Teste", 123, "02201123", "Apto 10", "Bairro Teste", "Cidade Teste", LocalDateTime.now(), LocalDateTime.now());
        UnidadeSaudeResponse ubs1 = new UnidadeSaudeResponse(UUID.randomUUID(), "UBS 1", enderecoResponse, LocalDateTime.now(), LocalDateTime.now());
        UnidadeSaudeResponse ubs2 = new UnidadeSaudeResponse(UUID.randomUUID(), "UBS 2", enderecoResponse, LocalDateTime.now(), LocalDateTime.now());
        List<UnidadeSaudeResponse> ubsList = List.of(ubs1, ubs2);

        when(ubsService.listarUbs()).thenReturn(ResponseEntity.ok(ubsList));

        inicializacaoService.run();

        verify(filaService, times(2)).salvarFila(argThat(f -> f.nomeFila().startsWith("atendimento.ubs-1")));
        verify(filaService, times(2)).salvarFila(argThat(f -> f.nomeFila().startsWith("atendimento.ubs-2")));
    }
}