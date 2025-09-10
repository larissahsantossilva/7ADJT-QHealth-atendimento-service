package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.domain.Fila;
import br.com.fiap.qhealth.ms.atendimento_service.entity.FilaEntity;
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
import static br.com.fiap.qhealth.ms.atendimento_service.utils.FilaUtils.converterParaFilaEntity;

@RequiredArgsConstructor
@Service
public class FilaService {

    public final Logger logger = LoggerFactory.getLogger(FilaService.class);
    public final FilaRepository filaRepository;

    public Fila salvarFila(Fila fila) {
        FilaEntity filaEntitySalva = filaRepository.save(converterParaFilaEntity(fila));
        logger.info("Fila salva com sucesso: {}", filaEntitySalva);
        return converterParaFila(filaEntitySalva);
    }

    public Fila buscarFila(UUID idFila){
        Optional<FilaEntity> filaEntity = filaRepository.findById(idFila);
        return converterParaFila(filaEntity.get());
    }

    public List<Fila> buscarFilas(){
        List<FilaEntity> filas = filaRepository.findAll();
        List<Fila> list = filas.stream().map(FilaUtils::converterParaFila).toList();
        return list;
    }
}
