package br.com.fiap.qhealth.ms.atendimento_service.repository;

import br.com.fiap.qhealth.ms.atendimento_service.model.Fila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FilaRepository extends JpaRepository<Fila, UUID> {
}