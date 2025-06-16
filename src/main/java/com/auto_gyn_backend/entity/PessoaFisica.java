package com.auto_gyn_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class PessoaFisica extends Pessoa {

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "dataNascimento", nullable = false)
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