package br.com.fiap.qhealth.ms.atendimento_service.utils;

import br.com.fiap.qhealth.ms.atendimento_service.domain.Atendimento;
import br.com.fiap.qhealth.ms.atendimento_service.domain.Fila;
import br.com.fiap.qhealth.ms.atendimento_service.entity.AtendimentoEntity;
import br.com.fiap.qhealth.ms.atendimento_service.entity.FilaEntity;

public class FilaUtils {

    public static FilaEntity converterParaFilaEntity(Fila fila) {
        return FilaEntity.builder()
                .id(fila.getId())
                .idUnidadeSaude(fila.getIdUnidadeSaude())
                .nomeFila(fila.getNomeFila())
                .tipoFila(fila.getTipoFila())
                .dataCriacao(fila.getDataCriacao())
                .dataUltimaAlteracao(fila.getDataUltimaAlteracao())
            .build();
    }

    public static Fila converterParaFila(FilaEntity filaEntity) {
        return Fila.builder()
                .id(filaEntity.getId())
                .idUnidadeSaude(filaEntity.getIdUnidadeSaude())
                .nomeFila(filaEntity.getNomeFila())
                .tipoFila(filaEntity.getTipoFila())
                .dataCriacao(filaEntity.getDataCriacao())
                .dataUltimaAlteracao(filaEntity.getDataUltimaAlteracao())
            .build();
    }
}
