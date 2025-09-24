package br.com.fiap.qhealth.ms.atendimento_service.external.paciente;

import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.response.PacienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "usuarios-service", url = "http://atendimento-usuario:8080", path = "/api/v1/pacientes")
public interface PacienteClient {

    @GetMapping("/{id}")
    ResponseEntity<PacienteResponse> buscarPacientePorId(@PathVariable("id") String cpf);
}
