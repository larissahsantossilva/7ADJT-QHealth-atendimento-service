package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.entity.AtendimentoEntity;
import br.com.fiap.qhealth.ms.atendimento_service.model.AtendimentoDto;
import br.com.fiap.qhealth.ms.atendimento_service.model.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.repository.AtendimentoRepository;
import br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AtendimentoServiceTest {

    private AtendimentoRepository atendimentoRepository;
    private AtendimentoService atendimentoService;

    @BeforeEach
    void setUp() {
        atendimentoRepository = mock(AtendimentoRepository.class);
        atendimentoService = new AtendimentoService(atendimentoRepository);
    }

    @Test
    void testSalvarAtendimento() {
        UUID id = UUID.randomUUID();
        UUID anamneseId = UUID.randomUUID();
        UUID filaId = UUID.randomUUID();
        String cpf = "12345678900";
        LocalDateTime now = LocalDateTime.now();

        AtendimentoDto dto = new AtendimentoDto(id, cpf, anamneseId, now, now);
        FilaDto filaDto = new FilaDto(filaId, UUID.randomUUID(), "Fila Teste", "Normal", now, now);
        AtendimentoEntity entity = AtendimentoUtils.converterParaAtendimento(dto, filaDto);

        when(atendimentoRepository.save(any(AtendimentoEntity.class))).thenReturn(entity);

        AtendimentoDto result = atendimentoService.salvarAtendimento(dto, filaDto);

        assertEquals(dto.id(), result.id());
        assertEquals(dto.cpf(), result.cpf());
        assertEquals(dto.idAnamnese(), result.idAnamnese());
    }

    @Test
    void testBuscarAtendimentos() {
        UUID id = UUID.randomUUID();
        UUID anamneseId = UUID.randomUUID();
        String cpf = "12345678900";
        LocalDateTime now = LocalDateTime.now();

        AtendimentoEntity entity = AtendimentoEntity.builder()
                .id(id)
                .idAnamnese(anamneseId)
                .cpf(cpf)
                .dataCriacao(now)
                .dataUltimaAlteracao(now)
            .build();

        when(atendimentoRepository.findAll()).thenReturn(List.of(entity));

        List<AtendimentoDto> result = atendimentoService.buscarAtendimentos();

        assertEquals(1, result.size());
        assertEquals(id, result.get(0).id());
        assertEquals(cpf, result.get(0).cpf());
        assertEquals(anamneseId, result.get(0).idAnamnese());
    }
}