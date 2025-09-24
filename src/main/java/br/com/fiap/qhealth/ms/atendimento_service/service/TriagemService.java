package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.TriagemClient;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.request.TriagemRequest;
import br.com.fiap.qhealth.ms.atendimento_service.external.triagem.response.TriagemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TriagemService {
    private final TriagemClient triagemClient;

    public ResponseEntity<TriagemResponse> definirTriagem(TriagemRequest triagemRequest) {
        ResponseEntity<TriagemResponse> triagemResponse = triagemClient.definirTriagem(triagemRequest);
        return triagemResponse;
    }
}
