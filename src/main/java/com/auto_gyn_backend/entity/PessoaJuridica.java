package com.auto_gyn_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class PessoaJuridica extends Pessoa {

    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @Column(name = "razaoSocial", nullable = false)
    private String razaoSocial;

    public PessoaJuridica() {
    }

    public PessoaJuridica(long id, String nome, String endereco, String telefone, String cnpj, String razaoSocial) {
        super(id, nome, endereco, telefone);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
}
