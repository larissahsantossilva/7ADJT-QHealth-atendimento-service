package br.com.fiap.qhealth.ms.atendimento_service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AtendimentoListener {

    private static final Logger log = LoggerFactory.getLogger(AtendimentoListener.class);
    public static final String QUEUE_NAME = "fila-novo-atendimento";

    @RabbitListener(queues = QUEUE_NAME)
    public void escutarMensagem(String mensagem) {
        // Esta anotação transforma o método num consumidor da fila especificada.
        // O Spring irá chamar este método automaticamente sempre que uma nova
        // mensagem chegar à "fila-atendimento".

        log.info(">>> Mensagem recebida da fila [{}]: '{}'", QUEUE_NAME, mensagem);

        // Aqui, você adicionaria a sua lógica de negócio para processar a mensagem.
        // Por exemplo: salvar no banco de dados, chamar outro serviço, etc.
    }
}
