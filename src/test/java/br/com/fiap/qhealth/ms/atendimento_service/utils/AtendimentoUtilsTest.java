package br.com.fiap.qhealth.ms.atendimento_service.utils;

import br.com.fiap.qhealth.ms.atendimento_service.dto.AtendimentoDto;
import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.model.Atendimento;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AtendimentoUtilsTest {

    @Test
    void testConverterParaAtendimentoDTO() {
        UUID id = UUID.randomUUID();
        UUID anamneseId = UUID.randomUUID();
        UUID filaId = UUID.randomUUID();
        String cpf = "12345678900";
        LocalDateTime now = LocalDateTime.now();

        AtendimentoRequestJson requestJson = new AtendimentoRequestJson(id, cpf,false, false, false, false, now, now);
        FilaDto filaDto = new FilaDto(filaId, UUID.randomUUID(), "Fila Teste", "Normal", now, now);

        AtendimentoDto dto = AtendimentoUtils.converterParaAtendimentoDTO(requestJson, anamneseId, filaDto);

        assertEquals(id, dto.id());
        assertEquals(cpf, dto.cpf());
        assertEquals(anamneseId, dto.idAnamnese());
        assertEquals(now, dto.dataCriacao());
        assertEquals(now, dto.dataUltimaAlteracao());
    }

    @Test
    void testConverterParaAtendimentoEntity() {
        UUID id = UUID.randomUUID();
        UUID anamneseId = UUID.randomUUID();
        UUID filaId = UUID.randomUUID();
        String cpf = "12345678900";
        LocalDateTime now = LocalDateTime.now();

        AtendimentoDto dto = new AtendimentoDto(id, cpf, anamneseId, now, now);
        FilaDto filaDto = new FilaDto(filaId, UUID.randomUUID(), "Fila Teste", "Normal", now, now);

        Atendimento entity = AtendimentoUtils.converterParaAtendimento(dto, filaDto);

        assertEquals(id, entity.getId());
        assertEquals(anamneseId, entity.getIdAnamnese());
        assertEquals(cpf, entity.getCpf());
        assertEquals(now, entity.getDataCriacao());
        assertEquals(now, entity.getDataUltimaAlteracao());
        assertNotNull(entity.getFila());
    }

    @Test
    void testConverterParaAtendimentoDTOFromEntity() {
        UUID id = UUID.randomUUID();
        UUID anamneseId = UUID.randomUUID();
        String cpf = "12345678900";
        LocalDateTime now = LocalDateTime.now();

        Atendimento entity = Atendimento.builder()
                .id(id)
                .idAnamnese(anamneseId)
                .cpf(cpf)
                .dataCriacao(now)
                .dataUltimaAlteracao(now)
                .build();

        AtendimentoDto dto = AtendimentoUtils.converterParaAtendimentoDTO(entity);

        assertEquals(id, dto.id());
        assertEquals(anamneseId, dto.idAnamnese());
        assertEquals(cpf, dto.cpf());
        assertEquals(now, dto.dataCriacao());
        assertEquals(now, dto.dataUltimaAlteracao());
    }
}