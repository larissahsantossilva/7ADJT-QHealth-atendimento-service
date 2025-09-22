package br.com.fiap.qhealth.ms.atendimento_service.listener;

import br.com.fiap.qhealth.ms.atendimento_service.dto.AtendimentoDTO;
import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDTO;
import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.request.AnamneseRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.response.PacienteResponse;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.request.TriagemAnamneseRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.request.TriagemRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.response.TriagemResponse;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;
import br.com.fiap.qhealth.ms.atendimento_service.service.*;
import br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.fiap.qhealth.ms.atendimento_service.config.RabbitMQConfig.QUEUE_NAME;

@RequiredArgsConstructor
@Service
public class AtendimentoListener {

    private static final Logger log = LoggerFactory.getLogger(AtendimentoListener.class);
    private final AtendimentoService atendimentoService;
    private final FilaService filaService;
    private final AnamneseService anamneseService;
    private final TriagemService triagemService;
    private final PacienteService pacienteService;

    @RabbitListener(queues = QUEUE_NAME)
    public void escutarMensagem(AtendimentoRequestJson atendimentoRequestJson) {
        log.info(">>> Mensagem recebida da fila [{}]: '{}'", QUEUE_NAME, atendimentoRequestJson);

        UUID anamneseId = criarAnamnese(atendimentoRequestJson);
        PacienteResponse pacienteResponse = buscarPaciente(atendimentoRequestJson.pacienteId());
        TriagemResponse triagemResponse = definirTriagem(anamneseId, atendimentoRequestJson, pacienteResponse);
        FilaDTO fila = escolherFila();
        AtendimentoDTO atendimento = salvarAtendimento(atendimentoRequestJson, fila, anamneseId);

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

    private PacienteResponse buscarPaciente(UUID pacienteId) {
        ResponseEntity<PacienteResponse> response = pacienteService.buscarPacientePorId(pacienteId);
        return response.getBody();
    }

    private FilaDTO escolherFila() {
        // TODO: Implement logic to choose the correct queue
        return filaService.buscarFila(UUID.fromString("c1b2a3d4-e5f6-a7b8-c9d0-a1b2c3d4e5f6"));
    }

    private AtendimentoDTO salvarAtendimento(AtendimentoRequestJson requestJson, FilaDTO fila, UUID anamneseId) {
        AtendimentoDTO atendimentoDTO = AtendimentoUtils.converterParaAtendimentoDTO(requestJson, anamneseId, fila);
            return atendimentoService.salvarAtendimento(atendimentoDTO, fila);
    }
}