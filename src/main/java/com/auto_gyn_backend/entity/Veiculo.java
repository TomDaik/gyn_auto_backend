package com.auto_gyn_backend.entity;

import jakarta.persistence.*;

@Entity
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVeiculo;

    @Column
    private String marca;

    @Column
    private String modelo;

    public Veiculo() {
    }

    public Veiculo(int idVeiculo, String marca, String modelo) {
        this.idVeiculo = idVeiculo;
        this.marca = marca;
        this.modelo = modelo;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
