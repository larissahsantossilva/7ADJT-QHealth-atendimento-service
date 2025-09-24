package br.com.fiap.qhealth.ms.atendimento_service.listener;

import br.com.fiap.qhealth.ms.atendimento_service.model.AtendimentoDto;
import br.com.fiap.qhealth.ms.atendimento_service.model.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.request.AnamneseRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.response.PacienteResponse;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.response.TriagemResponse;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;
import br.com.fiap.qhealth.ms.atendimento_service.producer.AtendimentoProducer;
import br.com.fiap.qhealth.ms.atendimento_service.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe AtendimentoListener.
 * Foco em validar a orquestração e a chamada dos serviços mockados.
 */
@ExtendWith(MockitoExtension.class) // Habilita o Mockito para JUnit 5
class AtendimentoListenerTest {

    @Mock
    private AtendimentoService atendimentoService;
    @Mock
    private FilaService filaService;
    @Mock
    private AnamneseService anamneseService;
    @Mock
    private TriagemService triagemService;
    @Mock
    private PacienteService pacienteService;
    @Mock
    private AtendimentoProducer atendimentoProducer;

    @InjectMocks
    private AtendimentoListener atendimentoListener;

    private AtendimentoRequestJson requestJson;

    @BeforeEach
    void setUp() {
        // Prepara um objeto de request padrão para os testes
        requestJson = new AtendimentoRequestJson(
            UUID.randomUUID(),           // id
            "12345678900",               // cpf
            true,                        // fumante
            false,                       // gravida
            true,                        // diabetico
            false,                       // hipertenso
            LocalDateTime.now(),         // dataCriacao
            LocalDateTime.now()          // dataUltimaAlteracao
        );
    }

    @Test
    void deveProcessarMensagemEChamarTodosOsServicosCorretamenteParaPacienteSemPrioridade() {
        UUID anamneseId = UUID.randomUUID();
        when(anamneseService.criarAnamnese(any(AnamneseRequest.class))).thenReturn(ResponseEntity.ok(anamneseId));

        PacienteResponse pacienteResponse = mockPacienteResponse();
        when(pacienteService.buscarPacientePorId(anyString())).thenReturn(ResponseEntity.ok(pacienteResponse));

        TriagemResponse triagemResponse = new TriagemResponse(false);
        when(triagemService.definirTriagem(any())).thenReturn(ResponseEntity.ok(triagemResponse));

        FilaDto filaDto = mockFilaDto();
        when(filaService.buscarFilaPorNomeFila(contains("atendimento.ubs-"))).thenReturn(filaDto);

        AtendimentoDto atendimentoDto = mockAtendimentoDto(anamneseId);
        when(atendimentoService.salvarAtendimento(any(), any())).thenReturn(atendimentoDto);

        doNothing().when(atendimentoProducer).enviarAtendimentoParaFila(any(), any(), any(), any());

        // Act (Execução do método a ser testado)
        atendimentoListener.escutarMensagem(requestJson);

        // Assert (Verificação das interações)
        verify(anamneseService, times(1)).criarAnamnese(any(AnamneseRequest.class));
        verify(pacienteService, times(1)).buscarPacientePorId("12345678900");
        verify(triagemService, times(1)).definirTriagem(any());
        verify(filaService, times(1)).buscarFilaPorNomeFila(anyString());
        verify(atendimentoService, times(1)).salvarAtendimento(any(), eq(filaDto));
        verify(atendimentoProducer, times(1)).enviarAtendimentoParaFila(any(), eq(filaDto.nomeFila()), eq(filaDto.nomeFila()), anyString());
    }

    @Test
    void deveProcessarMensagemEChamarTodosOsServicosCorretamenteParaPacienteComPrioridade() {
        UUID anamneseId = UUID.randomUUID();
        when(anamneseService.criarAnamnese(any(AnamneseRequest.class))).thenReturn(ResponseEntity.ok(anamneseId));

        PacienteResponse pacienteResponse = mockPacienteResponse();
        when(pacienteService.buscarPacientePorId(anyString())).thenReturn(ResponseEntity.ok(pacienteResponse));

        TriagemResponse triagemResponse = new TriagemResponse(true);
        when(triagemService.definirTriagem(any())).thenReturn(ResponseEntity.ok(triagemResponse));

        FilaDto filaDto = mockFilaDto();
        when(filaService.buscarFilaPorNomeFila(contains("atendimento.ubs-"))).thenReturn(filaDto);

        AtendimentoDto atendimentoDto = mockAtendimentoDto(anamneseId);
        when(atendimentoService.salvarAtendimento(any(), any())).thenReturn(atendimentoDto);

        doNothing().when(atendimentoProducer).enviarAtendimentoParaFila(any(), any(), any(), any());

        atendimentoListener.escutarMensagem(requestJson);

        // Assert (Verificação das interações)
        verify(anamneseService, times(1)).criarAnamnese(any(AnamneseRequest.class));
        verify(pacienteService, times(1)).buscarPacientePorId("12345678900");
        verify(triagemService, times(1)).definirTriagem(any());
        verify(filaService, times(1)).buscarFilaPorNomeFila(anyString());
        verify(atendimentoService, times(1)).salvarAtendimento(any(), eq(filaDto));
        verify(atendimentoProducer, times(1)).enviarAtendimentoParaFila(any(), eq(filaDto.nomeFila()), eq(filaDto.nomeFila()), anyString());
    }

    private static AtendimentoDto mockAtendimentoDto(UUID anamneseId) {
        return new AtendimentoDto(UUID.randomUUID(), "12345678900", anamneseId, LocalDateTime.now(), LocalDateTime.now());
    }

    private static PacienteResponse mockPacienteResponse() {
        return new PacienteResponse(
            UUID.randomUUID(),
            "John Doe",
            "john.doe@example.com",
            "johndoe",
            "12345678900",
            "M",
            "+5511999999999",
            LocalDate.of(1990, 1, 1),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    private static FilaDto mockFilaDto() {
        return new FilaDto(UUID.randomUUID(), UUID.randomUUID(), "atendimento.ubs-1", "NORMAL", LocalDateTime.now(), LocalDateTime.now());
    }
}
