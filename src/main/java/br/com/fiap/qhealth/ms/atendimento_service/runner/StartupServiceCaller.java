package br.com.fiap.qhealth.ms.atendimento_service.runner; // Adapte o package para o seu projeto

import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDTO;
import br.com.fiap.qhealth.ms.atendimento_service.service.FilaService; // Importe o seu serviço
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class StartupServiceCaller implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupServiceCaller.class);

    // 1. Injetamos o serviço que queremos chamar
    private final FilaService filaService;

    /**
     * Este método é executado automaticamente pelo Spring Boot após a inicialização da aplicação.
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("==========================================================");
        log.info("=== EXECUTANDO TAREFAS DE INICIALIZAÇÃO DA APLICAÇÃO ===");

        // 2. Chamamos o método do nosso serviço
        try {
            //filaService.verificarECriarFilasPadrao();
            List<FilaDTO> filaDtos = filaService.buscarFilas();
            filaDtos.forEach(fila -> log.info("Fila existente: {}", fila));

            log.info(">>> Lógica de inicialização do FilaService executada com sucesso.");
        } catch (Exception e) {
            log.error("!!! Falha ao executar a lógica de inicialização do FilaService.", e);
        }

        log.info("=== TAREFAS DE INICIALIZAÇÃO CONCLUÍDAS ===");
        log.info("==========================================================");
    }
}