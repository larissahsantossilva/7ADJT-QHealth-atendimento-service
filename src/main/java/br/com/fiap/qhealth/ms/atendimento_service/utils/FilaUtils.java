package br.com.fiap.qhealth.ms.atendimento_service.utils;

import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDTO;
import br.com.fiap.qhealth.ms.atendimento_service.model.Fila;

public class FilaUtils {

    public static Fila converterParaFila(FilaDTO filaDTO) {
        return Fila.builder()
                .id(filaDTO.getId())
                .idUnidadeSaude(filaDTO.getIdUnidadeSaude())
                .nomeFila(filaDTO.getNomeFila())
                .tipoFila(filaDTO.getTipoFila())
                .dataCriacao(filaDTO.getDataCriacao())
                .dataUltimaAlteracao(filaDTO.getDataUltimaAlteracao())
            .build();
    }

    public static FilaDTO converterParaFilaDTO(Fila fila) {
        return FilaDTO.builder()
                .id(fila.getId())
                .idUnidadeSaude(fila.getIdUnidadeSaude())
                .nomeFila(fila.getNomeFila())
                .tipoFila(fila.getTipoFila())
                .dataCriacao(fila.getDataCriacao())
                .dataUltimaAlteracao(fila.getDataUltimaAlteracao())
            .build();
    }
}
