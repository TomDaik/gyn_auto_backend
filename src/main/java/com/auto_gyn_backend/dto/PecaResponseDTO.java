package com.auto_gyn_backend.dto;

import java.util.List;

public record PecaResponseDTO(
        Long id,
        String codigo,
        String descricao,
        String tipo,
        double valorUnitario,
        int quantidade,
        List<FornecedorDTO> fornecedores // Retorna uma lista com os detalhes dos fornecedores
) {}
