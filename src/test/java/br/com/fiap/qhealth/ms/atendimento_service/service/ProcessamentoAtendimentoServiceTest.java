package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.dto.AtendimentoDto;
import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.request.AnamneseRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.response.PacienteResponse;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.request.TriagemRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.response.TriagemResponse;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;
import br.com.fiap.qhealth.ms.atendimento_service.producer.AtendimentoProducer;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessamentoAtendimentoServiceTest {

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
    private ProcessamentoAtendimentoService processamentoAtendimentoService;

    private AtendimentoRequestJson requestJson;
    private PacienteResponse pacienteResponse;
    private TriagemResponse triagemResponse;
    private FilaDto filaDto;
    private AtendimentoDto atendimentoDto;
    private UUID anamneseId;

    @BeforeEach
    void setUp() {
        requestJson = new AtendimentoRequestJson(UUID.randomUUID(), "12345678900", true, false, true, false, LocalDateTime.now(), LocalDateTime.now());
        pacienteResponse = new PacienteResponse(UUID.randomUUID(), "Nome", "email@example.com", "login", "12345678900", "M", "999999999", LocalDate.now().minusYears(30), LocalDateTime.now(), LocalDateTime.now());
        triagemResponse = new TriagemResponse(false);
        filaDto = new FilaDto(UUID.randomUUID(), UUID.randomUUID(), "atendimento.ubs-1", "NORMAL", LocalDateTime.now(), LocalDateTime.now());
        anamneseId = UUID.randomUUID();
        atendimentoDto = new AtendimentoDto(UUID.randomUUID(), "12345678900", anamneseId, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void deveProcessarNovoAtendimentoComSucesso() {
        when(pacienteService.buscarPacientePorId(anyString())).thenReturn(ResponseEntity.ok(pacienteResponse));
        when(anamneseService.criarAnamnese(any(AnamneseRequest.class))).thenReturn(ResponseEntity.ok(anamneseId));
        when(triagemService.definirTriagem(any(TriagemRequest.class))).thenReturn(ResponseEntity.ok(triagemResponse));
        when(filaService.buscarFilaPorNomeFila(anyString())).thenReturn(filaDto);
        when(atendimentoService.salvarAtendimento(any(AtendimentoDto.class), any(FilaDto.class))).thenReturn(atendimentoDto);
        doNothing().when(atendimentoProducer).enviarAtendimentoParaFila(any(), anyString(), anyString(), anyString());

        processamentoAtendimentoService.processarNovoAtendimento(requestJson);

        verify(pacienteService).buscarPacientePorId(requestJson.cpf());
        verify(anamneseService).criarAnamnese(any(AnamneseRequest.class));
        verify(triagemService).definirTriagem(any(TriagemRequest.class));
        verify(filaService).buscarFilaPorNomeFila(anyString());
        verify(atendimentoService).salvarAtendimento(any(AtendimentoDto.class), any(FilaDto.class));
        verify(atendimentoProducer).enviarAtendimentoParaFila(any(), anyString(), anyString(), anyString());
    }

    @Test
    void deveLancarExcecaoQuandoPacienteNaoEncontrado() {
        when(pacienteService.buscarPacientePorId(anyString())).thenReturn(ResponseEntity.ok(null));

        assertThrows(RuntimeException.class, () -> processamentoAtendimentoService.processarNovoAtendimento(requestJson));

        verify(pacienteService).buscarPacientePorId(requestJson.cpf());
        verifyNoInteractions(anamneseService, triagemService, filaService, atendimentoService, atendimentoProducer);
    }

    @Test
    void deveLancarExcecaoQuandoFalharAoCriarAnamnese() {
        when(pacienteService.buscarPacientePorId(anyString())).thenReturn(ResponseEntity.ok(pacienteResponse));
        when(anamneseService.criarAnamnese(any(AnamneseRequest.class))).thenReturn(ResponseEntity.ok(null));

        assertThrows(RuntimeException.class, () -> processamentoAtendimentoService.processarNovoAtendimento(requestJson));

        verify(pacienteService).buscarPacientePorId(requestJson.cpf());
        verify(anamneseService).criarAnamnese(any(AnamneseRequest.class));
        verifyNoInteractions(triagemService, filaService, atendimentoService, atendimentoProducer);
    }

    @Test
    void deveLancarExcecaoQuandoFalharAoDefinirTriagem() {
        when(pacienteService.buscarPacientePorId(anyString())).thenReturn(ResponseEntity.ok(pacienteResponse));
        when(anamneseService.criarAnamnese(any(AnamneseRequest.class))).thenReturn(ResponseEntity.ok(anamneseId));
        when(triagemService.definirTriagem(any(TriagemRequest.class))).thenReturn(ResponseEntity.ok(null));

        assertThrows(RuntimeException.class, () -> processamentoAtendimentoService.processarNovoAtendimento(requestJson));

        verify(pacienteService).buscarPacientePorId(requestJson.cpf());
        verify(anamneseService).criarAnamnese(any(AnamneseRequest.class));
        verify(triagemService).definirTriagem(any(TriagemRequest.class));
        verifyNoInteractions(filaService, atendimentoService, atendimentoProducer);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarFila() {
        when(pacienteService.buscarPacientePorId(anyString())).thenReturn(ResponseEntity.ok(pacienteResponse));
        when(anamneseService.criarAnamnese(any(AnamneseRequest.class))).thenReturn(ResponseEntity.ok(anamneseId));
        when(triagemService.definirTriagem(any(TriagemRequest.class))).thenReturn(ResponseEntity.ok(triagemResponse));
        when(filaService.buscarFilaPorNomeFila(anyString())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> processamentoAtendimentoService.processarNovoAtendimento(requestJson));

        verify(pacienteService).buscarPacientePorId(requestJson.cpf());
        verify(anamneseService).criarAnamnese(any(AnamneseRequest.class));
        verify(triagemService).definirTriagem(any(TriagemRequest.class));
        verify(filaService).buscarFilaPorNomeFila(anyString());
        verifyNoInteractions(atendimentoService, atendimentoProducer);
    }
}
