package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.model.AtendimentoDto;
import br.com.fiap.qhealth.ms.atendimento_service.model.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.entity.AtendimentoEntity;
import br.com.fiap.qhealth.ms.atendimento_service.repository.AtendimentoRepository;
import br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils.converterParaAtendimento;
import static br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils.converterParaAtendimentoDTO;

@RequiredArgsConstructor
@Service
public class AtendimentoService {
    public final Logger logger = LoggerFactory.getLogger(AtendimentoService.class);
    public final AtendimentoRepository atendimentoRepository;

    public AtendimentoDto salvarAtendimento(AtendimentoDto atendimento, FilaDto fila) {
        AtendimentoEntity atendimentoEntity = atendimentoRepository.save(converterParaAtendimento(atendimento, fila));
        logger.info("Atendimento salvo com sucesso: {}", atendimentoEntity);
        return converterParaAtendimentoDTO(atendimentoEntity);
    }

    public List<AtendimentoDto> buscarAtendimentos(){
        List<AtendimentoEntity> atendimentos = atendimentoRepository.findAll();
        List<AtendimentoDto> list = atendimentos.stream().map(AtendimentoUtils::converterParaAtendimentoDTO).toList();
        return list;
    }
}
