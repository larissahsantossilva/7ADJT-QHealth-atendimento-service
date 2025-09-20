package br.com.fiap.qhealth.ms.atendimento_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "triagem-service", url = "http://triagem:8080", path = "/api/v1/triagens")
public interface TriagemClient {

//    @GetMapping
//    ResponseEntity<List<AnamneseResponse>> listarAnamneses(
//        @RequestParam("page") int page,
//        @RequestParam("size") int size
//    );

//    @GetMapping("/{id}")
//    ResponseEntity<AnamneseResponse> buscarAnamnesePorId(@PathVariable("id") UUID id);
//
    @PostMapping
    ResponseEntity<TriagemResponse> definirTriagem(@RequestBody TriagemRequest triagemRequest);

//    @PutMapping("/{id}")
//    ResponseEntity<String> atualizarAnamnese(
//            @PathVariable("id") UUID id,
//            @RequestBody AnamneseRequest anamneseRequest
//    );
//
//    @DeleteMapping("/{id}")
//    ResponseEntity<Void> excluirAnamnesePorId(@PathVariable("id") UUID id);
}
