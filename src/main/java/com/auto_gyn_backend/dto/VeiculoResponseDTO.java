package com.auto_gyn_backend.dto;

public record VeiculoResponseDTO(
        Long idVeiculo,
        String marca,
        String modelo,
        Integer ano,
        String placa,
        Integer quilometragem,
        ProprietarioDTO proprietario
) {}
