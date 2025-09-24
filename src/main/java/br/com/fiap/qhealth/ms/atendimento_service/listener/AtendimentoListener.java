package br.com.fiap.qhealth.ms.atendimento_service.listener;

import br.com.fiap.qhealth.ms.atendimento_service.config.RabbitMQConfig;
import br.com.fiap.qhealth.ms.atendimento_service.model.AtendimentoDto;
import br.com.fiap.qhealth.ms.atendimento_service.model.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.request.AnamneseRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.response.PacienteResponse;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.request.TriagemAnamneseRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.request.TriagemRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.response.TriagemResponse;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;
import br.com.fiap.qhealth.ms.atendimento_service.producer.AtendimentoProducer;
import br.com.fiap.qhealth.ms.atendimento_service.producer.json.AtendimentoUbsRequestJson;
import br.com.fiap.qhealth.ms.atendimento_service.service.*;
import br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.fiap.qhealth.ms.atendimento_service.config.RabbitMQConfig.QUEUE_NOVO_ATENDIMENTO;

@RequiredArgsConstructor
@Service
public class AtendimentoListener {
    private static final Logger log = LoggerFactory.getLogger(AtendimentoListener.class);
    private final AtendimentoService atendimentoService;
    private final FilaService filaService;
    private final AnamneseService anamneseService;
    private final TriagemService triagemService;
    private final PacienteService pacienteService;
    private final AtendimentoProducer atendimentoProducer;

    @RabbitListener(queues = QUEUE_NOVO_ATENDIMENTO)
    public void escutarMensagem(AtendimentoRequestJson atendimentoRequestJson) {
        log.info(">>> Mensagem recebida da fila [{}]: '{}'", QUEUE_NOVO_ATENDIMENTO, atendimentoRequestJson);

        UUID anamneseId = criarAnamnese(atendimentoRequestJson);
        PacienteResponse pacienteResponse = buscarPaciente(atendimentoRequestJson.cpf());
        TriagemResponse triagemResponse = definirTriagem(anamneseId, atendimentoRequestJson, pacienteResponse);
        FilaDto fila = escolherFila(triagemResponse);
        AtendimentoDto atendimento = salvarAtendimento(atendimentoRequestJson, fila, anamneseId);
        AtendimentoUbsRequestJson atendimentoUbsRequestJson = new AtendimentoUbsRequestJson(
            atendimento.id(),
            atendimento.cpf(),
            fila.id(),
            atendimento.dataCriacao(),
            atendimento.dataUltimaAlteracao()
        );
        atendimentoProducer.enviarAtendimentoParaFila(
            atendimentoUbsRequestJson,
            fila.nomeFila(),
            fila.nomeFila(),
            RabbitMQConfig.EXCHANGE_NAME
        );

        log.info(">>> Atendimento salvo: {}", atendimento);
    }

    private UUID criarAnamnese(AtendimentoRequestJson requestJson) {
        AnamneseRequest anamneseRequest = new AnamneseRequest(
            requestJson.diabetico(),
            requestJson.hipertenso(),
            requestJson.fumante(),
            requestJson.gravida()
        );
        ResponseEntity<UUID> response = anamneseService.criarAnamnese(anamneseRequest);
        return response.getBody();
    }

    private TriagemResponse definirTriagem(UUID anamneseId, AtendimentoRequestJson requestJson, PacienteResponse pacienteResponse) {
        TriagemAnamneseRequest triagemAnamneseRequest = new TriagemAnamneseRequest(
            anamneseId,
            requestJson.fumante(),
            requestJson.gravida(),
            requestJson.diabetico(),
            requestJson.hipertenso()
        );
        TriagemRequest triagemRequest = new TriagemRequest(
            pacienteResponse.dataNascimento(),
            triagemAnamneseRequest
        );
        ResponseEntity<TriagemResponse> response = triagemService.definirTriagem(triagemRequest);
        return response.getBody();
    }

    private PacienteResponse buscarPaciente(String cpf) {
        ResponseEntity<PacienteResponse> response = pacienteService.buscarPacientePorId(cpf);
        return response.getBody();
    }

    private FilaDto escolherFila(TriagemResponse triagemResponse) {
        int resultado = randomOneOrTwo();
        FilaDto filaDTO = null;
        if(triagemResponse.preferencial()){
            filaDTO = filaService.buscarFilaPorNomeFila("atendimento.ubs-" + resultado + "-preferencial");
        } else {
            filaDTO = filaService.buscarFilaPorNomeFila("atendimento.ubs-" + resultado);
        }
        return filaDTO;
    }

    private AtendimentoDto salvarAtendimento(AtendimentoRequestJson requestJson, FilaDto fila, UUID anamneseId) {
        AtendimentoDto atendimentoDTO = AtendimentoUtils.converterParaAtendimentoDTO(requestJson, anamneseId, fila);
        return atendimentoService.salvarAtendimento(atendimentoDTO, fila);
    }

    public static int randomOneOrTwo() {
        return 1 + java.util.concurrent.ThreadLocalRandom.current().nextInt(2);
    }
}