package com.auto_gyn_backend.dto;

public record PessoaJuridicaDTO(
        Long id,
        String nome,
        String endereco,
        String telefone,
        String cnpj,
        String razaoSocial
) {
}
