package br.com.fiap.qhealth.ms.atendimento_service.utils;

import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.model.Fila;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FilaUtilsTest {

    @Test
    void testConverterParaFilaEntity() {
        UUID id = UUID.randomUUID();
        UUID unidadeId = UUID.randomUUID();
        String nome = "Fila Teste";
        String tipo = "Normal";
        LocalDateTime now = LocalDateTime.now();

        FilaDto filaDto = new FilaDto(id, unidadeId, nome, tipo, now, now);

        Fila entity = FilaUtils.converterParaFila(filaDto);

        assertEquals(id, entity.getId());
        assertEquals(unidadeId, entity.getUnidadeSaudeId());
        assertEquals(nome, entity.getNomeFila());
        assertEquals(tipo, entity.getTipoFila());
        assertEquals(now, entity.getDataCriacao());
        assertEquals(now, entity.getDataUltimaAlteracao());
    }

    @Test
    void testConverterParaFilaDto() {
        UUID id = UUID.randomUUID();
        UUID unidadeId = UUID.randomUUID();
        String nome = "Fila Teste";
        String tipo = "Normal";
        LocalDateTime now = LocalDateTime.now();

        Fila entity = Fila.builder()
                .id(id)
                .unidadeSaudeId(unidadeId)
                .nomeFila(nome)
                .tipoFila(tipo)
                .dataCriacao(now)
                .dataUltimaAlteracao(now)
            .build();

        FilaDto filaDto = FilaUtils.converterParaFilaDTO(entity);

        assertEquals(id, filaDto.id());
        assertEquals(unidadeId, filaDto.unidadeSaudeId());
        assertEquals(nome, filaDto.nomeFila());
        assertEquals(tipo, filaDto.tipoFila());
        assertEquals(now, filaDto.dataCriacao());
        assertEquals(now, filaDto.dataUltimaAlteracao());
    }
}
