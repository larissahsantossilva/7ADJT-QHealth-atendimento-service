package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.PacienteClient;
import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.response.PacienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PacienteService {

    private final PacienteClient pacienteClient;

    public ResponseEntity<PacienteResponse> buscarPacientePorId(String cpf) {
        ResponseEntity<PacienteResponse> pacienteResponse = pacienteClient.buscarPacientePorId(cpf);
        return pacienteResponse;
    }
}
