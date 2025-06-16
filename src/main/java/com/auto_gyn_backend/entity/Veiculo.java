package com.auto_gyn_backend.entity;

import jakarta.persistence.*;

@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVeiculo;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @Column(name = "quilometragem", nullable = false)
    private Integer quilometragem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private PessoaFisica proprietario; // <<< ALTERAÇÃO AQUI

    public Veiculo() {
    }

    public Veiculo(Long idVeiculo, String marca, String modelo, Integer ano, String placa, Integer quilometragem, PessoaFisica proprietario) {
        this.idVeiculo = idVeiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.placa = placa;
        this.quilometragem = quilometragem;
        this.proprietario = proprietario;
    }

    public long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
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

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }

    public PessoaFisica getProprietario() {
        return proprietario;
    }

    public void setProprietario(PessoaFisica proprietario) {
        this.proprietario = proprietario;
    }
}