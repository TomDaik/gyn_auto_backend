package com.auto_gyn_backend.dto;

import com.auto_gyn_backend.enums.StatusOrdemServico;
import java.time.LocalDate;
import java.util.List;

// DTO de resposta com todos os detalhes de uma OS
public record OrdemServicoResponseDTO(
        Long id,
        LocalDate dataAbertura,
        LocalDate dataFechamento,
        StatusOrdemServico status,
        String observacoes,
        Double valorTotal,
        // Detalhes do Cliente, Veículo e Itens
        ProprietarioDTO cliente,
        VeiculoResponseDTO veiculo,
        List<ItemServicoResponseDTO> itensServico,
        List<ItemPecaResponseDTO> itensPeca
) {}
