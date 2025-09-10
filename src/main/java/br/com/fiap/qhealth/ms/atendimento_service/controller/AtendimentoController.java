package br.com.fiap.qhealth.ms.atendimento_service.controller;

import br.com.fiap.qhealth.ms.atendimento_service.domain.Atendimento;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;
import br.com.fiap.qhealth.ms.atendimento_service.producer.AtendimentoProducer;
import br.com.fiap.qhealth.ms.atendimento_service.service.AtendimentoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/atendimentos")
public class AtendimentoController {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AtendimentoController.class);
    private final AtendimentoProducer atendimentoProducer;
    private final AtendimentoService atendimentoService;

    public AtendimentoController(AtendimentoProducer atendimentoProducer, AtendimentoService atendimentoService) {
        this.atendimentoProducer = atendimentoProducer;
        this.atendimentoService = atendimentoService;
    }

    @GetMapping("/health")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping
    public String getAtendimentos() {
        List<Atendimento> atendimentos = atendimentoService.buscarAtendimentos();
        LOGGER.info("Atendimentos encontrados: {}", atendimentos.size());
        return "Atendimentos encontrados: " + atendimentos.size();
    }

    @PostMapping
    public ResponseEntity<String> criarAtendimento(@RequestBody AtendimentoRequestJson atendimentoRequestJson) {
        atendimentoProducer.enviarAtendimentoParaFila(atendimentoRequestJson);
        return ResponseEntity.ok("Atendimento created");
    }
}
