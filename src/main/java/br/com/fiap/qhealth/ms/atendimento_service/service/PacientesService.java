package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.dto.AtendimentoDTO;
import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDTO;
import br.com.fiap.qhealth.ms.atendimento_service.model.Atendimento;
import br.com.fiap.qhealth.ms.atendimento_service.repository.AtendimentoRepository;
import br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils.converterParaAtendimento;
import static br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils.converterParaAtendimentoDTO;

@RequiredArgsConstructor
@Service
public class PacientesService {

    private final PacientesClient pacientesClient;

    public ResponseEntity<PacienteResponse> buscarPacientePorId(UUID id) {
        ResponseEntity<PacienteResponse> pacienteResponseResponseEntity = pacientesClient.buscarPacientePorId(id);
        return pacienteResponseResponseEntity;
    }
}
