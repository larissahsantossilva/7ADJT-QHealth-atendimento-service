package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.AnamneseClient;
import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.request.AnamneseRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.anamnese.response.AnamneseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnamneseService {
    private final AnamneseClient anamneseClient;

    public ResponseEntity<List<AnamneseResponse>> listarAnamneses() {
        ResponseEntity<List<AnamneseResponse>> listResponseEntity = anamneseClient.listarAnamneses(0, 10);
        return listResponseEntity;
    }

    public ResponseEntity<UUID> criarAnamnese(AnamneseRequest anamneseRequest) {
        ResponseEntity<UUID> responseEntity = anamneseClient.criarAnamnese(anamneseRequest);
        return responseEntity;
    }
}
