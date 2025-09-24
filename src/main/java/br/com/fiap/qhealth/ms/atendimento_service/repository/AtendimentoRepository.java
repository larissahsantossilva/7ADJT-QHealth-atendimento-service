package br.com.fiap.qhealth.ms.atendimento_service.repository;

import br.com.fiap.qhealth.ms.atendimento_service.entity.AtendimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AtendimentoRepository extends JpaRepository<AtendimentoEntity, UUID> {
}
