package br.com.fiap.qhealth.ms.atendimento_service.utils;

import br.com.fiap.qhealth.ms.atendimento_service.model.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.entity.FilaEntity;

public class FilaUtils {

    public static FilaEntity converterParaFila(FilaDto filaDTO) {
        return FilaEntity.builder()
                .id(filaDTO.id())
                .idUnidadeSaude(filaDTO.idUnidadeSaude())
                .nomeFila(filaDTO.nomeFila())
                .tipoFila(filaDTO.tipoFila())
                .dataCriacao(filaDTO.dataCriacao())
                .dataUltimaAlteracao(filaDTO.dataUltimaAlteracao())
            .build();
    }

    public static FilaDto converterParaFilaDTO(FilaEntity fila) {
        return new FilaDto(
            fila.getId(),
            fila.getIdUnidadeSaude(),
            fila.getNomeFila(),
            fila.getTipoFila(),
            fila.getDataCriacao(),
            fila.getDataUltimaAlteracao()
        );
    }
}