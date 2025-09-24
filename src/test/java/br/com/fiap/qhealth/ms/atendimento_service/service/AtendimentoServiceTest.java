package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.model.Atendimento;
import br.com.fiap.qhealth.ms.atendimento_service.dto.AtendimentoDto;
import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDto;
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
        Atendimento entity = AtendimentoUtils.converterParaAtendimento(dto, filaDto);

        when(atendimentoRepository.save(any(Atendimento.class))).thenReturn(entity);

        AtendimentoDto result = atendimentoService.salvarAtendimento(dto, filaDto);

        assertEquals(dto.id(), result.id());
        assertEquals(dto.cpf(), result.cpf());
        assertEquals(dto.anamneseId(), result.anamneseId());
    }

    @Test
    void testBuscarAtendimentos() {
        UUID id = UUID.randomUUID();
        UUID anamneseId = UUID.randomUUID();
        String cpf = "12345678900";
        LocalDateTime now = LocalDateTime.now();

        Atendimento entity = Atendimento.builder()
                .id(id)
                .anamneseId(anamneseId)
                .cpf(cpf)
                .dataCriacao(now)
                .dataUltimaAlteracao(now)
            .build();

        when(atendimentoRepository.findAll()).thenReturn(List.of(entity));

        List<AtendimentoDto> result = atendimentoService.buscarAtendimentos();

        assertEquals(1, result.size());
        assertEquals(id, result.get(0).id());
        assertEquals(cpf, result.get(0).cpf());
        assertEquals(anamneseId, result.get(0).anamneseId());
    }
}