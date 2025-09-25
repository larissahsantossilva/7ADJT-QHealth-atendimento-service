package br.com.fiap.qhealth.ms.atendimento_service.listener;

import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;
import br.com.fiap.qhealth.ms.atendimento_service.service.ProcessamentoAtendimentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtendimentoListenerTest {

    @Mock
    private ProcessamentoAtendimentoService processamentoService;

    @InjectMocks
    private AtendimentoListener atendimentoListener;

    private AtendimentoRequestJson requestJson;

    @BeforeEach
    void setUp() {
        requestJson = new AtendimentoRequestJson(
            UUID.randomUUID(),
            "12345678900",
            true,
            false,
            true,
            false,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    @Test
    void deveDelegarProcessamentoAoReceberMensagem() {
        // Arrange
        doNothing().when(processamentoService).processarNovoAtendimento(requestJson);

        // Act
        atendimentoListener.escutarMensagem(requestJson);

        // Assert
        verify(processamentoService, times(1)).processarNovoAtendimento(requestJson);
    }

    @Test
    void deveLidarComExcecaoDoServicoDeProcessamento() {
        // Arrange
        doThrow(new RuntimeException("Erro ao processar")).when(processamentoService).processarNovoAtendimento(requestJson);

        // Act
        atendimentoListener.escutarMensagem(requestJson);

        // Assert
        verify(processamentoService, times(1)).processarNovoAtendimento(requestJson);
        // A asserção aqui é que o listener não relança a exceção, tratando-a internamente (logando o erro).
    }
}