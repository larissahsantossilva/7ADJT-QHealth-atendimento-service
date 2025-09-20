package br.com.fiap.qhealth.ms.atendimento_service.listener;

import br.com.fiap.qhealth.ms.atendimento_service.dto.AtendimentoDTO;
import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDTO;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;
import br.com.fiap.qhealth.ms.atendimento_service.service.*;
import br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static br.com.fiap.qhealth.ms.atendimento_service.config.RabbitMQConfig.QUEUE_NAME;

@Service
public class AtendimentoListener {

    private static final Logger log = LoggerFactory.getLogger(AtendimentoListener.class);
    private final AtendimentoService atendimentoService;
    private final FilaService filaService;
    private final AnamneseService anamneseService;
    private final TriagemService triagemService;

    public AtendimentoListener(AtendimentoService atendimentoService, FilaService filaService, AnamneseService anamneseService, TriagemService triagemService) {
        this.atendimentoService = atendimentoService;
        this.filaService = filaService;
        this.anamneseService = anamneseService;
        this.triagemService = triagemService;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void escutarMensagem(AtendimentoRequestJson atendimentoRequestJson) {
        log.info(">>> Mensagem recebida da fila [{}]: '{}'", QUEUE_NAME, atendimentoRequestJson);
        ResponseEntity<List<AnamneseResponse>> listResponseEntity = anamneseService.listarAnamneses();
        AnamneseRequest anamneseRequest = new AnamneseRequest();
        anamneseRequest.setDiabetico(atendimentoRequestJson.diabetico());
        anamneseRequest.setHipertenso(atendimentoRequestJson.hipertenso());
        anamneseRequest.setFumante(atendimentoRequestJson.fumante());
        anamneseRequest.setGravida(atendimentoRequestJson.gravida());
        ResponseEntity<UUID> uuidResponseEntity = anamneseService.criarAnamnese(anamneseRequest);
        ResponseEntity<List<AnamneseResponse>> listResponseEntity2 = anamneseService.listarAnamneses();

        TriagemAnamneseRequest triagemAnamneseRequest = new TriagemAnamneseRequest();
        triagemAnamneseRequest.setId(uuidResponseEntity.getBody());
        triagemAnamneseRequest.setDiabetico(atendimentoRequestJson.diabetico());
        triagemAnamneseRequest.setFumante(atendimentoRequestJson.fumante());
        triagemAnamneseRequest.setGravida(atendimentoRequestJson.gravida());
        triagemAnamneseRequest.setHipertenso(atendimentoRequestJson.hipertenso());

        TriagemRequest triagemRequest = new TriagemRequest();
        triagemRequest.setDataNascimento(LocalDate.now());
        triagemRequest.setAnamnese(triagemAnamneseRequest);

        ResponseEntity<TriagemResponse> triagemResponseResponseEntity = triagemService.definirTriagem(triagemRequest);
        List<FilaDTO> filas = filaService.buscarFilas();
        FilaDTO fila = filaService.buscarFila(UUID.fromString("c1b2a3d4-e5f6-a7b8-c9d0-a1b2c3d4e5f6"));//Falta lógica para escolher a fila correta
        List<AtendimentoDTO> atendimentos = atendimentoService.buscarAtendimentos();
        AtendimentoDTO atendimento = atendimentoService.salvarAtendimento(AtendimentoUtils.converterParaAtendimentoDTO(atendimentoRequestJson), fila);
        log.info(">>> Atendimento salvo: {}", atendimento);
    }
}
