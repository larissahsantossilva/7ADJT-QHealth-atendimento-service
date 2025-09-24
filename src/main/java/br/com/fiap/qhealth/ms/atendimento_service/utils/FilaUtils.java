package br.com.fiap.qhealth.ms.atendimento_service.utils;

import br.com.fiap.qhealth.ms.atendimento_service.dto.FilaDto;
import br.com.fiap.qhealth.ms.atendimento_service.model.Fila;

public class FilaUtils {

    public static Fila converterParaFila(FilaDto filaDTO) {
        return Fila.builder()
                .id(filaDTO.id())
                .idUnidadeSaude(filaDTO.idUnidadeSaude())
                .nomeFila(filaDTO.nomeFila())
                .tipoFila(filaDTO.tipoFila())
                .dataCriacao(filaDTO.dataCriacao())
                .dataUltimaAlteracao(filaDTO.dataUltimaAlteracao())
            .build();
    }

    public static FilaDto converterParaFilaDTO(Fila fila) {
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