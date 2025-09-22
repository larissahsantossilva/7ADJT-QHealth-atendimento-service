package br.com.fiap.qhealth.ms.atendimento_service.producer;

import br.com.fiap.qhealth.ms.atendimento_service.config.RabbitMQConfig;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;
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

    public void enviarAtendimentoParaFila(AtendimentoRequestJson payload) {
        try {
            log.info(">>> Enviando para a fila [{}]: {}", RabbitMQConfig.QUEUE_NOVO_ATENDIMENTO, payload);
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY_NOVO_ATENDIMENTO,
                payload
            );
        } catch (Exception e) {
            log.error("!!! Erro ao enviar mensagem para a fila: {}", e.getMessage());
        }
    }

}
