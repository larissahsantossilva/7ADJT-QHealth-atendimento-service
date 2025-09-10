package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.domain.Atendimento;
import br.com.fiap.qhealth.ms.atendimento_service.domain.Fila;
import br.com.fiap.qhealth.ms.atendimento_service.entity.AtendimentoEntity;
import br.com.fiap.qhealth.ms.atendimento_service.repository.AtendimentoRepository;
import br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils.converterParaAtendimento;
import static br.com.fiap.qhealth.ms.atendimento_service.utils.AtendimentoUtils.converterParaAtendimentoEntity;

@RequiredArgsConstructor
@Service
public class AtendimentoService {

    public final Logger logger = LoggerFactory.getLogger(AtendimentoService.class);
    public final AtendimentoRepository atendimentoRepository;

    public Atendimento salvarAtendimento(Atendimento atendimento, Fila fila) {
        atendimento.setIdFila(fila.getId());
        AtendimentoEntity atendimentoEntity = atendimentoRepository.save(converterParaAtendimentoEntity(atendimento));
        logger.info("Atendimento salvo com sucesso: {}", atendimentoEntity);
        return converterParaAtendimento(atendimentoEntity);
    }

    public List<Atendimento> buscarAtendimentos(){
        List<AtendimentoEntity> atendimentos = atendimentoRepository.findAll();
        List<Atendimento> list = atendimentos.stream().map(AtendimentoUtils::converterParaAtendimento).toList();
        return list;
    }
}
