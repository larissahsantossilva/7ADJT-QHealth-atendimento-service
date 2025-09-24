package br.com.fiap.qhealth.ms.atendimento_service.producer;

import br.com.fiap.qhealth.ms.atendimento_service.producer.json.AtendimentoUbsRequestJson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtendimentoProducer {
    private static final Logger log = LoggerFactory.getLogger(AtendimentoProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public void enviarAtendimentoParaFila(AtendimentoUbsRequestJson payload, String nomeFila, String routingKey, String exchange) {
        try {
            log.info(">>> Enviando para a fila [{}]: {}", nomeFila, payload);
            rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                payload
            );
        } catch (Exception e) {
            log.error("!!! Erro ao enviar mensagem para a fila: {}", e.getMessage());
        }
    }
}
