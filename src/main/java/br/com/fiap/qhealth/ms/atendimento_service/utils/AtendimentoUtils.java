package br.com.fiap.qhealth.ms.atendimento_service.utils;

import br.com.fiap.qhealth.ms.atendimento_service.dto.AtendimentoDTO;
import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDTO;
import br.com.fiap.qhealth.ms.atendimento_service.model.Atendimento;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;

public class AtendimentoUtils {

    public static AtendimentoDTO converterParaAtendimentoDTO(AtendimentoRequestJson atendimentoRequestJson) {
        return AtendimentoDTO.builder()
                .id(atendimentoRequestJson.id())
                .idPaciente(atendimentoRequestJson.pacienteId())
                .posicaoFila(atendimentoRequestJson.posicaoFila())
                .dataCriacao(atendimentoRequestJson.dataCriacao())
                .dataUltimaAlteracao(atendimentoRequestJson.dataUltimaAlteracao())
            .build();
    }

    public static Atendimento converterParaAtendimento(AtendimentoDTO atendimentoDTO, FilaDTO filaDTO) {
        Atendimento atendimento = Atendimento.builder()
                .id(atendimentoDTO.getId())
                .idAnamnese(atendimentoDTO.getIdAnamnese())
                .idPaciente(atendimentoDTO.getIdPaciente())
                .fila(FilaUtils.converterParaFila(filaDTO))
                .posicaoFila(atendimentoDTO.getPosicaoFila())
                .dataCriacao(atendimentoDTO.getDataCriacao())
                .dataUltimaAlteracao(atendimentoDTO.getDataUltimaAlteracao())
            .build();
        return atendimento;
    }

    public static AtendimentoDTO converterParaAtendimentoDTO(Atendimento atendimento) {
        return AtendimentoDTO.builder()
                .id(atendimento.getId())
                .idAnamnese(atendimento.getIdAnamnese())
                .idPaciente(atendimento.getIdPaciente())
                .posicaoFila(atendimento.getPosicaoFila())
                .dataCriacao(atendimento.getDataCriacao())
                .dataUltimaAlteracao(atendimento.getDataUltimaAlteracao())
            .build();
    }
}
