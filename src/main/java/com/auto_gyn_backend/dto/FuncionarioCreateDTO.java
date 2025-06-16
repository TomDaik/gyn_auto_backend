package com.auto_gyn_backend.dto;

import java.time.LocalDate;

public record FuncionarioCreateDTO(
        String nome,
        String endereco,
        String telefone,
        String cpf,
        LocalDate dataNascimento,
        String cargo,
        double salario
) {
}
