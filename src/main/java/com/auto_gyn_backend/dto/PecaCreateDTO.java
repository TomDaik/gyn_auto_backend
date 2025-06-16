package com.auto_gyn_backend.dto;

import java.util.List;

public record PecaCreateDTO(
        String codigo,
        String descricao,
        String tipo,
        double valorUnitario,
        int quantidade,
        List<Long> idsFornecedores // Recebe uma lista de IDs dos fornecedores
) {
}
