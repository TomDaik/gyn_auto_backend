package com.auto_gyn_backend.dto;

import java.time.LocalDate;

public record FuncionarioDTO(
        Long id,
        String nome,
        String cpf,
        String cargo,
        double salario,
        LocalDate dataNascimento
) {
}
