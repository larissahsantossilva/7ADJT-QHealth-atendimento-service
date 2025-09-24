package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.model.Fila;
import br.com.fiap.qhealth.ms.atendimento_service.repository.FilaRepository;
import br.com.fiap.qhealth.ms.atendimento_service.utils.FilaUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.fiap.qhealth.ms.atendimento_service.utils.FilaUtils.converterParaFila;
import static br.com.fiap.qhealth.ms.atendimento_service.utils.FilaUtils.converterParaFilaDTO;

@RequiredArgsConstructor
@Service
public class FilaService {
    public final Logger logger = LoggerFactory.getLogger(FilaService.class);
    public final FilaRepository filaRepository;

    public FilaDto salvarFila(FilaDto fila) {
        Fila filaEntity = filaRepository.save(converterParaFila(fila));
        logger.info("Fila salva com sucesso: {}", filaEntity);
        return converterParaFilaDTO(filaEntity);
    }

    public FilaDto buscarFila(UUID idFila){
        Optional<Fila> filaEntity = filaRepository.findById(idFila);
        return converterParaFilaDTO(filaEntity.get());
    }

    public List<FilaDto> buscarFilas(){
        List<Fila> filas = filaRepository.findAll();
        List<FilaDto> list = filas.stream().map(FilaUtils::converterParaFilaDTO).toList();
        return list;
    }

    public FilaDto buscarFilaPorNomeFila(String nomeFila){
        Fila fila = filaRepository.findByNomeFila(nomeFila);
        return converterParaFilaDTO(fila);
    }
}
