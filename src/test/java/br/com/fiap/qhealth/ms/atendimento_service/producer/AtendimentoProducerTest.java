package br.com.fiap.qhealth.ms.atendimento_service.producer;

import br.com.fiap.qhealth.ms.atendimento_service.producer.json.AtendimentoUbsRequestJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe AtendimentoProducer.
 * Foco em validar a interação com o RabbitTemplate.
 */
@ExtendWith(MockitoExtension.class)
class AtendimentoProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private AtendimentoProducer atendimentoProducer;

    @Test
    void deveEnviarMensagemParaFilaCorretamente() {
        // Arrange
        AtendimentoUbsRequestJson payload = new AtendimentoUbsRequestJson(
            UUID.randomUUID(),
            "12345678900",
            UUID.randomUUID(),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        String nomeFila = "fila-teste";
        String routingKey = "routing-key-teste";
        String exchange = "exchange-teste";

        // Configura o mock para não fazer nada quando o método for chamado
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), any(AtendimentoUbsRequestJson.class));

        // Act
        atendimentoProducer.enviarAtendimentoParaFila(payload, nomeFila, routingKey, exchange);

        // Assert
        // Captura os argumentos que foram passados para o método convertAndSend
        ArgumentCaptor<String> exchangeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<AtendimentoUbsRequestJson> payloadCaptor = ArgumentCaptor.forClass(AtendimentoUbsRequestJson.class);

        // Verifica se o método foi chamado exatamente 1 vez e captura os argumentos
        verify(rabbitTemplate, times(1)).convertAndSend(
            exchangeCaptor.capture(),
            routingKeyCaptor.capture(),
            payloadCaptor.capture()
        );

        // Valida se os argumentos capturados são os esperados
        assertThat(exchangeCaptor.getValue()).isEqualTo(exchange);
        assertThat(routingKeyCaptor.getValue()).isEqualTo(routingKey);
        assertThat(payloadCaptor.getValue()).isEqualTo(payload);
    }

    @Test
    void naoDeveLancarExcecaoQuandoEnvioFalha() {
        // Arrange
        AtendimentoUbsRequestJson payload = new AtendimentoUbsRequestJson(null, null, null, null, null);
        String nomeFila = "fila-teste";
        String routingKey = "routing-key-teste";
        String exchange = "exchange-teste";

        // Configura o mock para lançar uma exceção quando o método for chamado
        doThrow(new AmqpException("Erro de conexão simulado")).when(rabbitTemplate).convertAndSend(anyString(), anyString(), any(AtendimentoUbsRequestJson.class));

        // Act & Assert
        // Verifica que, mesmo com a exceção do RabbitTemplate, o nosso producer a captura e não a lança para cima.
        assertDoesNotThrow(() -> {
            atendimentoProducer.enviarAtendimentoParaFila(payload, nomeFila, routingKey, exchange);
        });

        // Garante que a tentativa de envio foi feita
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any(AtendimentoUbsRequestJson.class));
    }
}