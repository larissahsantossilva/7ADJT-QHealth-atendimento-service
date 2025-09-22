package br.com.fiap.qhealth.ms.atendimento_service.utils;

import br.com.fiap.qhealth.ms.atendimento_service.dto.AtendimentoDTO;
import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDTO;
import br.com.fiap.qhealth.ms.atendimento_service.model.Atendimento;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;

import java.util.UUID;

public class AtendimentoUtils {

    public static AtendimentoDTO converterParaAtendimentoDTO(AtendimentoRequestJson atendimentoRequestJson, UUID anamneseId, FilaDTO fila) {
        AtendimentoDTO atendimentoDto = AtendimentoDTO.builder()
                .id(atendimentoRequestJson.id())
                .idPaciente(atendimentoRequestJson.pacienteId())
                .idAnamnese(anamneseId)
                .idFila(fila.getId())
                .dataCriacao(atendimentoRequestJson.dataCriacao())
                .dataUltimaAlteracao(atendimentoRequestJson.dataUltimaAlteracao())
            .build();
        return atendimentoDto;
    }

    public static Atendimento converterParaAtendimento(AtendimentoDTO atendimentoDTO, FilaDTO filaDTO) {
        Atendimento atendimento = Atendimento.builder()
                .id(atendimentoDTO.getId())
                .idAnamnese(atendimentoDTO.getIdAnamnese())
                .idPaciente(atendimentoDTO.getIdPaciente())
                .fila(FilaUtils.converterParaFila(filaDTO))
                .dataCriacao(atendimentoDTO.getDataCriacao())
                .dataUltimaAlteracao(atendimentoDTO.getDataUltimaAlteracao())
            .build();
        return atendimento;
    }

    public static AtendimentoDTO converterParaAtendimentoDTO(Atendimento atendimento) {
        AtendimentoDTO atendimentoDto = AtendimentoDTO.builder()
                .id(atendimento.getId())
                .idAnamnese(atendimento.getIdAnamnese())
                .idPaciente(atendimento.getIdPaciente())
                .dataCriacao(atendimento.getDataCriacao())
                .dataUltimaAlteracao(atendimento.getDataUltimaAlteracao())
            .build();
        return atendimentoDto;
    }
}
