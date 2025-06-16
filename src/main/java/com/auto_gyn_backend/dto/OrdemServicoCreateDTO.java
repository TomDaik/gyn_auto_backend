package com.auto_gyn_backend.dto;

import java.util.List;

public record OrdemServicoCreateDTO(
        Long idCliente,
        Long idVeiculo,
        String observacoes,
        List<ItemServicoDTO> servicos,
        List<ItemPecaDTO> pecas
) {}
