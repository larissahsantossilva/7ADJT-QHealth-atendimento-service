package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.external.ubs.response.UnidadeSaudeResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class InicializacaoService implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(InicializacaoService.class);

    private final FilaService filaService;
    private final UbsService ubsService;

    @Override
    public void run(String... args) {
        log.info("==========================================================");
        log.info("=== EXECUTANDO TAREFAS DE INICIALIZAÇÃO DA APLICAÇÃO ===");

        try {
            List<UnidadeSaudeResponse> ubsList = listarUbs();
            log.info("Tamanho de lista UBS: {}", ubsList.size());
            log.info("UBS existente: {}", ubsList);

            createFilasForUbs(ubsList);

            log.info(">>> Lógica de inicialização do FilaService executada com sucesso.");
        } catch (Exception e) {
            log.error("!!! Falha ao executar a lógica de inicialização do FilaService.", e);
        }

        log.info("=== TAREFAS DE INICIALIZAÇÃO CONCLUÍDAS ===");
        log.info("==========================================================");
    }

    private List<UnidadeSaudeResponse> listarUbs() {
        ResponseEntity<List<UnidadeSaudeResponse>> response = ubsService.listarUbs();
        return response.getBody();
    }

    private void createFilasForUbs(List<UnidadeSaudeResponse> ubsList) {
        if(ubsList != null && !ubsList.isEmpty()) {
            for (int i = 0; i < ubsList.size(); i++) {
                UnidadeSaudeResponse ubs = ubsList.get(i);
                log.info("UBS existente: {}", ubs);
                filaService.salvarFila(new FilaDto(
                    null,
                    ubs.id(),
                    "atendimento.ubs-" + (i + 1),
                    "NORMAL",
                    null,
                    null
                ));
                filaService.salvarFila(new FilaDto(
                    null,
                    ubs.id(),
                    "atendimento.ubs-" + (i + 1) + "-preferencial",
                    "PREFERENCIAL",
                    null,
                    null
                ));
            }
        }
    }
}