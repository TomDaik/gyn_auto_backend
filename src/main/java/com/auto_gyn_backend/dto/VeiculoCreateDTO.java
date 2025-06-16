package com.auto_gyn_backend.dto;

public record VeiculoCreateDTO(
        String marca,
        String modelo,
        Integer ano,
        String placa,
        Integer quilometragem,
        Long idProprietario // Apenas o ID do proprietário é necessário para a associação
) {}
