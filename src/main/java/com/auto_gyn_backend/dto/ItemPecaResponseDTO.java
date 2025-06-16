package com.auto_gyn_backend.dto;

public record ItemPecaResponseDTO(
        String pecaDescricao,
        int quantidade,
        double valorUnitarioCobrado,
        double valorTotalItem
) {
}
