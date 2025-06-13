package com.auto_gyn_backend.dto;

import java.time.LocalDate;

public record PessoaFisicaCreateDTO(
        String nome,
        String endereco,
        String telefone,
        String cpf,
        LocalDate dataNascimento
) {}
