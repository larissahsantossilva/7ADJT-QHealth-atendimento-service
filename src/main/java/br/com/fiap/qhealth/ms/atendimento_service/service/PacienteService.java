package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.PacienteClient;
import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.response.PacienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils.converterParaAtendimentoDTO;

@RequiredArgsConstructor
@Service
public class PacienteService {

    private final PacienteClient pacienteClient;

    public ResponseEntity<PacienteResponse> buscarPacientePorId(UUID id) {
        ResponseEntity<PacienteResponse> pacienteResponseResponseEntity = pacienteClient.buscarPacientePorId(id);
        return pacienteResponseResponseEntity;
    }
}
