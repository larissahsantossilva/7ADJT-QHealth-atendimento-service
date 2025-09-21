package br.com.fiap.qhealth.ms.atendimento_service.external.anamnese;

import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.request.AnamneseRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.response.AnamneseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "anamnese-service", url = "http://atendimento-usuario:8080", path = "/api/v1/anamneses")
public interface AnamneseClient {

    @GetMapping
    ResponseEntity<List<AnamneseResponse>> listarAnamneses(
        @RequestParam("page") int page,
        @RequestParam("size") int size
    );

    @PostMapping
    ResponseEntity<UUID> criarAnamnese(@RequestBody AnamneseRequest anamneseRequest);
}
