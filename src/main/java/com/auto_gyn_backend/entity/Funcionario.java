package com.auto_gyn_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "funcionario") // Garante que a tabela tenha um nome específico
public class Funcionario extends PessoaFisica {

    private String cargo;
    private double salario;

    public Funcionario() {
        super();
    }

    // Construtor para facilitar a criação
    public Funcionario(Long id, String nome, String endereco, String telefone, String cpf, LocalDate dataNascimento, String cargo, double salario) {
        super(id, nome, endereco, telefone, cpf, dataNascimento);
        this.cargo = cargo;
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}