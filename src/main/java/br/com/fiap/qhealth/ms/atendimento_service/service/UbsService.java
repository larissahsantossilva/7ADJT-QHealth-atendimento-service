package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.response.AnamneseResponse;
import br.com.fiap.qhealth.ms.atendimento_service.external.ubs.UbsClient;
import br.com.fiap.qhealth.ms.atendimento_service.external.ubs.response.UnidadeSaudeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UbsService {
    private final UbsClient ubsClient;

    public ResponseEntity<List<UnidadeSaudeResponse>> listarUbs() {
        ResponseEntity<List<UnidadeSaudeResponse>> listResponseEntity = ubsClient.listarUbs(0, 10);
        return listResponseEntity;
    }
}
