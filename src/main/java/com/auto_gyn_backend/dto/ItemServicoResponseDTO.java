package com.auto_gyn_backend.dto;

public record ItemServicoResponseDTO(
        String servicoDescricao,
        Double valorCobrado,
        String funcionarioNome)
{}
