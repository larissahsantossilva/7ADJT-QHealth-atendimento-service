package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.configuration.RabbitMQConfiguration;
import br.com.fiap.qhealth.ms.atendimento_service.dto.AtendimentoDto;
import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.request.AnamneseRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.response.PacienteResponse;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.request.TriagemAnamneseRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.request.TriagemRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.response.TriagemResponse;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;
import br.com.fiap.qhealth.ms.atendimento_service.producer.AtendimentoProducer;
import br.com.fiap.qhealth.ms.atendimento_service.producer.json.AtendimentoUbsRequestJson;
import br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProcessamentoAtendimentoService {

    private static final Logger log = LoggerFactory.getLogger(ProcessamentoAtendimentoService.class);

    private final AtendimentoService atendimentoService;
    private final FilaService filaService;
    private final AnamneseService anamneseService;
    private final TriagemService triagemService;
    private final PacienteService pacienteService;
    private final AtendimentoProducer atendimentoProducer;

    @Transactional
    public void processarNovoAtendimento(AtendimentoRequestJson requestJson) {
        log.info("Iniciando processamento do atendimento para o CPF {}", requestJson.cpf());

        PacienteResponse paciente = buscarPaciente(requestJson.cpf());
        UUID anamneseId = criarAnamnese(requestJson);
        TriagemResponse triagem = definirTriagem(anamneseId, requestJson, paciente);
        FilaDto fila = escolherFila(triagem);
        AtendimentoDto atendimento = salvarAtendimento(requestJson, fila, anamneseId);
        publicarAtendimentoParaUbs(atendimento, fila);

        log.info("Processamento do atendimento para o CPF {} finalizado com sucesso. ID do Atendimento: {}", requestJson.cpf(), atendimento.id());
    }

    private UUID criarAnamnese(AtendimentoRequestJson requestJson) {
        AnamneseRequest anamneseRequest = new AnamneseRequest(
            requestJson.diabetico(),
            requestJson.hipertenso(),
            requestJson.fumante(),
            requestJson.gravida()
        );
        return Optional.ofNullable(anamneseService.criarAnamnese(anamneseRequest))
                .map(ResponseEntity::getBody)
                .filter(Objects::nonNull)
                .orElseThrow(() -> new RuntimeException("Falha ao criar anamnese: resposta nula do serviço externo."));
    }

    private PacienteResponse buscarPaciente(String cpf) {
        return Optional.ofNullable(pacienteService.buscarPacientePorId(cpf))
                .map(ResponseEntity::getBody)
                .filter(Objects::nonNull)
                .orElseThrow(() -> new RuntimeException("Paciente com CPF " + cpf + " não encontrado."));
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
        return Optional.ofNullable(triagemService.definirTriagem(triagemRequest))
                .map(ResponseEntity::getBody)
                .filter(Objects::nonNull)
                .orElseThrow(() -> new RuntimeException("Falha ao definir a triagem: resposta nula do serviço externo."));
    }

    private FilaDto escolherFila(TriagemResponse triagemResponse) {
        int resultadoUbs = 1 + ThreadLocalRandom.current().nextInt(2);
        String nomeFila;
        if (triagemResponse.preferencial()) {
            nomeFila = "atendimento.ubs-" + resultadoUbs + "-preferencial";
        } else {
            nomeFila = "atendimento.ubs-" + resultadoUbs;
        }

        return Optional.ofNullable(filaService.buscarFilaPorNomeFila(nomeFila))
                .orElseThrow(() -> new RuntimeException("Não foi possível encontrar uma fila com o nome: " + nomeFila));
    }

    private AtendimentoDto salvarAtendimento(AtendimentoRequestJson requestJson, FilaDto fila, UUID anamneseId) {
        AtendimentoDto atendimentoDTO = AtendimentoUtils.converterParaAtendimentoDTO(requestJson, anamneseId, fila);
        return atendimentoService.salvarAtendimento(atendimentoDTO, fila);
    }

    private void publicarAtendimentoParaUbs(AtendimentoDto atendimento, FilaDto fila) {
        AtendimentoUbsRequestJson payload = new AtendimentoUbsRequestJson(
            atendimento.id(),
            atendimento.cpf(),
            fila.id(),
            atendimento.dataCriacao(),
            atendimento.dataUltimaAlteracao()
        );
        atendimentoProducer.enviarAtendimentoParaFila(
            payload,
            fila.nomeFila(),
            fila.nomeFila(), // Usando o nome da fila como routing key
            RabbitMQConfiguration.EXCHANGE_NAME
        );
    }
}