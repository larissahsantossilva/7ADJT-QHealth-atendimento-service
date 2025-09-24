package br.com.fiap.qhealth.ms.atendimento_service.utils;

import br.com.fiap.qhealth.ms.atendimento_service.dto.AtendimentoDto;
import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.model.Atendimento;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;

import java.util.UUID;

public class AtendimentoUtils {

    public static AtendimentoDto converterParaAtendimentoDTO(AtendimentoRequestJson atendimentoRequestJson, UUID anamneseId, FilaDto fila) {
        return new AtendimentoDto(
            atendimentoRequestJson.id(),
            atendimentoRequestJson.cpf(),
            anamneseId,
            atendimentoRequestJson.dataCriacao(),
            atendimentoRequestJson.dataUltimaAlteracao()
        );
    }

    public static Atendimento converterParaAtendimento(AtendimentoDto atendimentoDTO, FilaDto filaDTO) {
        return Atendimento.builder()
                .id(atendimentoDTO.id())
                .idAnamnese(atendimentoDTO.idAnamnese())
                .cpf(atendimentoDTO.cpf())
                .fila(FilaUtils.converterParaFila(filaDTO))
                .dataCriacao(atendimentoDTO.dataCriacao())
                .dataUltimaAlteracao(atendimentoDTO.dataUltimaAlteracao())
            .build();
    }

    public static AtendimentoDto converterParaAtendimentoDTO(Atendimento atendimento) {
        return new AtendimentoDto(
            atendimento.getId(),
            atendimento.getCpf(),
            atendimento.getIdAnamnese(),
            atendimento.getDataCriacao(),
            atendimento.getDataUltimaAlteracao()
        );
    }
}