package br.com.fiap.qhealth.ms.atendimento_service.external.paciente;

import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.response.PacienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "usuarios-service", url = "http://atendimento-usuario:8080", path = "/api/v1/pacientes")
public interface PacienteClient {

//    @GetMapping
//    ResponseEntity<List<AnamneseResponse>> listarAnamneses(
//        @RequestParam("page") int page,
//        @RequestParam("size") int size
//    );

    @GetMapping("/{id}")
    ResponseEntity<PacienteResponse> buscarPacientePorId(@PathVariable("id") UUID id);

//    @PostMapping
//    ResponseEntity<UUID> criarAnamnese(@RequestBody AnamneseRequest anamneseRequest);

//    @PutMapping("/{id}")
//    ResponseEntity<String> atualizarAnamnese(
//            @PathVariable("id") UUID id,
//            @RequestBody AnamneseRequest anamneseRequest
//    );
//
//    @DeleteMapping("/{id}")
//    ResponseEntity<Void> excluirAnamnesePorId(@PathVariable("id") UUID id);
}
