package com.auto_gyn_backend.dto;

public record PessoaJuridicaCreateDTO(
        String nome,
        String endereco,
        String telefone,
        String cnpj,
        String razaoSocial
) {
}
