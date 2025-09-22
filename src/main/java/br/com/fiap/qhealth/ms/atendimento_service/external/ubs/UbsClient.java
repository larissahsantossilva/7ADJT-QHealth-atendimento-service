package br.com.fiap.qhealth.ms.atendimento_service.external.ubs;

import br.com.fiap.qhealth.ms.atendimento_service.external.ubs.response.UnidadeSaudeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ubs-service", url = "http://ubs:8080", path = "/api/v1/unidades")
public interface UbsClient {

    @GetMapping
    ResponseEntity<List<UnidadeSaudeResponse>> listarUbs(
        @RequestParam("page") int page,
        @RequestParam("size") int size
    );
}
