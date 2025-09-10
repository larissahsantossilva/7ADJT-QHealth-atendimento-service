package br.com.fiap.qhealth.ms.atendimento_service.listener;

import br.com.fiap.qhealth.ms.atendimento_service.domain.Atendimento;
import br.com.fiap.qhealth.ms.atendimento_service.domain.Fila;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;
import br.com.fiap.qhealth.ms.atendimento_service.service.AtendimentoService;
import br.com.fiap.qhealth.ms.atendimento_service.service.FilaService;
import br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AtendimentoListener {

    private static final Logger log = LoggerFactory.getLogger(AtendimentoListener.class);
    public static final String QUEUE_NAME = "fila-novo-atendimento";
    private final AtendimentoService atendimentoService;
    private final FilaService filaService;

    public AtendimentoListener(AtendimentoService atendimentoService, FilaService filaService) {
        this.atendimentoService = atendimentoService;
        this.filaService = filaService;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void escutarMensagem(AtendimentoRequestJson atendimentoRequestJson) {
        log.info(">>> Mensagem recebida da fila [{}]: '{}'", QUEUE_NAME, atendimentoRequestJson);
        List<Fila> filas = filaService.buscarFilas();
        //Fila fila = filaService.buscarFila(UUID.fromString("c1b2a3d4-e5f6-a7b8-c9d0-a1b2c3d4e5f6"));
        Fila fila = Fila.builder()
                .id(UUID.fromString("c1b2a3d4-e5f6-a7b8-c9d0-a1b2c3d4e5f6"))
                .idUnidadeSaude(UUID.fromString("c1b2a3d4-e5f6-a7b8-c9d0-a1b2c3d4e5f6"))
                .nomeFila("Padrão")
                .tipoFila("NORMAL")
            .build();
        List<Atendimento> atendimentos = atendimentoService.buscarAtendimentos();
        Atendimento atendimento = atendimentoService.salvarAtendimento(AtendimentoUtils.converterParaAtendimento(atendimentoRequestJson), fila);
        log.info(">>> Atendimento salvo: {}", atendimento);
    }
}
