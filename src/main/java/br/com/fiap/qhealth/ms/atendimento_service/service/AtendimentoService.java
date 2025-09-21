package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.dto.AtendimentoDTO;
import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDTO;
import br.com.fiap.qhealth.ms.atendimento_service.model.Atendimento;
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
    public final FilaService filaService;

    public AtendimentoDTO salvarAtendimento(AtendimentoDTO atendimento, FilaDTO fila) {
        atendimento.setIdFila(fila.getId());
        Atendimento atendimentoEntity = atendimentoRepository.save(converterParaAtendimento(atendimento, fila));
        logger.info("Atendimento salvo com sucesso: {}", atendimentoEntity);
        return converterParaAtendimentoDTO(atendimentoEntity);
    }

    public List<AtendimentoDTO> buscarAtendimentos(){
        List<Atendimento> atendimentos = atendimentoRepository.findAll();
        List<AtendimentoDTO> list = atendimentos.stream().map(AtendimentoUtils::converterParaAtendimentoDTO).toList();
        return list;
    }
}
