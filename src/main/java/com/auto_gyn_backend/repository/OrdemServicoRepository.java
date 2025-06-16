package com.auto_gyn_backend.repository;

import com.auto_gyn_backend.entity.OrdemServico;
import com.auto_gyn_backend.enums.StatusOrdemServico; // Importar Enum
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Importar List

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    /** Busca todas as Ordens de Serviço de um cliente específico. (NOVO) */
    List<OrdemServico> findByClienteId(Long clienteId);

    /** Busca todas as Ordens de Serviço de um veículo específico. (NOVO) */
    List<OrdemServico> findByVeiculoIdVeiculo(Long veiculoId);

    /** Busca todas as Ordens de Serviço com um determinado status. (NOVO) */
    List<OrdemServico> findByStatus(StatusOrdemServico status);
}