package br.com.fiap.qhealth.ms.atendimento_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TriagemService {
    private final TriagemClient triagemClient;

    public ResponseEntity<TriagemResponse> definirTriagem(TriagemRequest triagemRequest) {
        ResponseEntity<TriagemResponse> responseEntity = triagemClient.definirTriagem(triagemRequest);
        return responseEntity;
    }
}
