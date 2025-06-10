package com.auto_gyn_backend.entity;

import java.time.LocalDate;

public class PessoaFisica extends Pessoa {
    private String cpf;
    private LocalDate dataNascimento;

    public PessoaFisica() {
    }

    public PessoaFisica(long id, String nome, String endereco, String telefone, String cpf, LocalDate dataNascimento) {
        super(id, nome, endereco, telefone);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
