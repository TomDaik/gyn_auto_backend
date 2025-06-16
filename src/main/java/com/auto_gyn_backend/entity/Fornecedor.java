package com.auto_gyn_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "fornecedor")
public class Fornecedor extends PessoaJuridica {

    // A classe Fornecedor herda todos os campos de PessoaJuridica (cnpj, razaoSocial)
    // e de Pessoa (id, nome, endereco, telefone).
    // Você pode adicionar campos específicos para um Fornecedor aqui se precisar,
    // como por exemplo: private String contatoVendedor;

    public Fornecedor() {
        super();
    }

    // Construtor para facilitar a criação de um fornecedor com todos os dados
    public Fornecedor(Long id, String nome, String endereco, String telefone, String cnpj, String razaoSocial) {
        super(id, nome, endereco, telefone, cnpj, razaoSocial);
    }
}