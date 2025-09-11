package br.com.fiap.qhealth.ms.atendimento_service.utils;

import br.com.fiap.qhealth.ms.atendimento_service.domain.Atendimento;
import br.com.fiap.qhealth.ms.atendimento_service.domain.Fila;
import br.com.fiap.qhealth.ms.atendimento_service.entity.AtendimentoEntity;
import br.com.fiap.qhealth.ms.atendimento_service.listener.json.AtendimentoRequestJson;

public class AtendimentoUtils {

    public static Atendimento converterParaAtendimento(AtendimentoRequestJson atendimentoRequestJson) {
        return Atendimento.builder()
                .id(atendimentoRequestJson.id())
                .idAnamnese(atendimentoRequestJson.idAnamnese())
                .idPaciente(atendimentoRequestJson.idPaciente())
                .posicaoFila(atendimentoRequestJson.posicaoFila())
                .dataCriacao(atendimentoRequestJson.dataCriacao())
                .dataUltimaAlteracao(atendimentoRequestJson.dataUltimaAlteracao())
            .build();
    }

    public static AtendimentoEntity converterParaAtendimentoEntity(Atendimento atendimento, Fila fila) {
        AtendimentoEntity atendimentoEntity = AtendimentoEntity.builder()
                .id(atendimento.getId())
                .idAnamnese(atendimento.getIdAnamnese())
                .idPaciente(atendimento.getIdPaciente())
                .fila(FilaUtils.converterParaFilaEntity(fila))
                .posicaoFila(atendimento.getPosicaoFila())
                .dataCriacao(atendimento.getDataCriacao())
                .dataUltimaAlteracao(atendimento.getDataUltimaAlteracao())
            .build();
        return atendimentoEntity;
    }

    public static Atendimento converterParaAtendimento(AtendimentoEntity atendimentoEntity) {
        return Atendimento.builder()
                .id(atendimentoEntity.getId())
                .idAnamnese(atendimentoEntity.getIdAnamnese())
                .idPaciente(atendimentoEntity.getIdPaciente())
                .posicaoFila(atendimentoEntity.getPosicaoFila())
                .dataCriacao(atendimentoEntity.getDataCriacao())
                .dataUltimaAlteracao(atendimentoEntity.getDataUltimaAlteracao())
            .build();
    }
}
