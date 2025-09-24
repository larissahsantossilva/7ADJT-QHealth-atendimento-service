package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.entity.FilaEntity;
import br.com.fiap.qhealth.ms.atendimento_service.model.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.repository.FilaRepository;
import br.com.fiap.qhealth.ms.atendimento_service.utils.FilaUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FilaServiceTest {

    private FilaRepository filaRepository;
    private FilaService filaService;

    @BeforeEach
    void setUp() {
        filaRepository = mock(FilaRepository.class);
        filaService = new FilaService(filaRepository);
    }

    @Test
    void testSalvarFila() {
        UUID id = UUID.randomUUID();
        UUID unidadeId = UUID.randomUUID();
        String nome = "Fila Teste";
        String tipo = "Normal";
        LocalDateTime now = LocalDateTime.now();

        FilaDto filaDto = new FilaDto(id, unidadeId, nome, tipo, now, now);
        FilaEntity entity = FilaUtils.converterParaFila(filaDto);

        when(filaRepository.save(any(FilaEntity.class))).thenReturn(entity);

        FilaDto result = filaService.salvarFila(filaDto);

        assertEquals(filaDto.id(), result.id());
        assertEquals(filaDto.nomeFila(), result.nomeFila());
    }

    @Test
    void testBuscarFila() {
        UUID id = UUID.randomUUID();
        FilaEntity entity = FilaEntity.builder().id(id).nomeFila("Fila Teste").build();

        when(filaRepository.findById(id)).thenReturn(Optional.of(entity));

        FilaDto result = filaService.buscarFila(id);

        assertEquals(id, result.id());
        assertEquals("Fila Teste", result.nomeFila());
    }

    @Test
    void testBuscarFilas() {
        FilaEntity entity = FilaEntity.builder().id(UUID.randomUUID()).nomeFila("Fila Teste").build();
        when(filaRepository.findAll()).thenReturn(List.of(entity));

        List<FilaDto> result = filaService.buscarFilas();

        assertEquals(1, result.size());
        assertEquals("Fila Teste", result.get(0).nomeFila());
    }

    @Test
    void testBuscarFilaPorNomeFila() {
        String nome = "Fila Teste";
        FilaEntity entity = FilaEntity.builder().id(UUID.randomUUID()).nomeFila(nome).build();

        when(filaRepository.findByNomeFila(nome)).thenReturn(entity);

        FilaDto result = filaService.buscarFilaPorNomeFila(nome);

        assertEquals(nome, result.nomeFila());
    }
}