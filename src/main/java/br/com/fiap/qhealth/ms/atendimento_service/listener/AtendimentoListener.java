package br.com.fiap.qhealth.ms.atendimento_service.listener;

import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;
import br.com.fiap.qhealth.ms.atendimento_service.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static br.com.fiap.qhealth.ms.atendimento_service.configuration.RabbitMQConfiguration.QUEUE_NOVO_ATENDIMENTO;

@RequiredArgsConstructor
@Component
public class AtendimentoListener {
    private static final Logger log = LoggerFactory.getLogger(AtendimentoListener.class);

    private final ProcessamentoAtendimentoService processamentoService;

    @RabbitListener(queues = QUEUE_NOVO_ATENDIMENTO)
    public void escutarMensagem(AtendimentoRequestJson atendimentoRequestJson) {
        log.info(">>> Mensagem recebida da fila [{}]: '{}'", QUEUE_NOVO_ATENDIMENTO, atendimentoRequestJson);
        try {
            // Delega todo o processamento para o serviço dedicado
            processamentoService.processarNovoAtendimento(atendimentoRequestJson);
        } catch (Exception e) {
            log.error("!!! Falha ao processar a mensagem de atendimento para o CPF {}: {}", atendimentoRequestJson.cpf(), e.getMessage());
            // Aqui, você poderia adicionar uma lógica para enviar a mensagem para uma Dead Letter Queue (DLQ)
        }
    }
}