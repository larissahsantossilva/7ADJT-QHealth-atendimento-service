package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDTO;
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

    public FilaDTO salvarFila(FilaDTO fila) {
        Fila filaEntitySalva = filaRepository.save(converterParaFila(fila));
        logger.info("Fila salva com sucesso: {}", filaEntitySalva);
        return converterParaFilaDTO(filaEntitySalva);
    }

    public FilaDTO buscarFila(UUID idFila){
        Optional<Fila> filaEntity = filaRepository.findById(idFila);
        return converterParaFilaDTO(filaEntity.get());
    }

    public List<FilaDTO> buscarFilas(){
        List<Fila> filas = filaRepository.findAll();
        List<FilaDTO> list = filas.stream().map(FilaUtils::converterParaFilaDTO).toList();
        return list;
    }
}
