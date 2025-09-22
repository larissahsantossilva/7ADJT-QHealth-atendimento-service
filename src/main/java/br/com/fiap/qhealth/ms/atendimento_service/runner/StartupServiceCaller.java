package br.com.fiap.qhealth.ms.atendimento_service.runner; // Adapte o package para o seu projeto

import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDTO;
import br.com.fiap.qhealth.ms.atendimento_service.external.ubs.response.UnidadeSaudeResponse;
import br.com.fiap.qhealth.ms.atendimento_service.service.FilaService; // Importe o seu serviço
import br.com.fiap.qhealth.ms.atendimento_service.service.UbsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class StartupServiceCaller implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupServiceCaller.class);

    // 1. Injetamos o serviço que queremos chamar
    private final FilaService filaService;
    private final UbsService ubsService;

    /**
     * Este método é executado automaticamente pelo Spring Boot após a inicialização da aplicação.
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("==========================================================");
        log.info("=== EXECUTANDO TAREFAS DE INICIALIZAÇÃO DA APLICAÇÃO ===");

        // 2. Chamamos o método do nosso serviço
        try {
            ResponseEntity<List<UnidadeSaudeResponse>> listResponseEntity = ubsService.listarUbs();
            List<UnidadeSaudeResponse> ubs = listResponseEntity.getBody();
            log.info("Tamanho de lista UBS: {} ", ubs.size());
            log.info("UBS existente: {}", ubs);
            for(int i = 0; i < ubs.size(); i++){
                log.info("UBS existente: {}", ubs.get(i));
                filaService.salvarFila(
                    new FilaDTO(
                        null,
                        ubs.get(i).id(),
                        "atendimento.ubs-" + (i+1),
                        "NORMAL",
                        null,
                        null
                    )
                );
                filaService.salvarFila(
                    new FilaDTO(
                        null,
                        ubs.get(i).id(),
                        "atendimento.ubs-" + (i+1) + "-preferencial",
                        "PREFERENCIAL",
                        null,
                        null
                    )
                );
            }
            log.info(">>> Lógica de inicialização do FilaService executada com sucesso.");
        } catch (Exception e) {
            log.error("!!! Falha ao executar a lógica de inicialização do FilaService.", e);
        }
        log.info("=== TAREFAS DE INICIALIZAÇÃO CONCLUÍDAS ===");
        log.info("==========================================================");
    }
}