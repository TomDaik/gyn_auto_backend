package com.auto_gyn_backend.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "peca")
public class Peca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    // O campo "fornecedor" como String foi REMOVIDO.
    // Em seu lugar, temos a relação Muitos-para-Muitos abaixo.

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "peca_fornecedor", // Nome da tabela de ligação no banco de dados
            joinColumns = @JoinColumn(name = "peca_id"), // Coluna que referencia a Peca
            inverseJoinColumns = @JoinColumn(name = "fornecedor_id") // Coluna que referencia o Fornecedor
    )
    private List<Fornecedor> fornecedores = new ArrayList<>();

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "valor_unitario", nullable = false)
    private double valorUnitario;

    @Column(name = "quantidade_estoque", nullable = false)
    private int quantidade;

    public Peca() {
    }

    // Construtor foi atualizado para remover o antigo campo "fornecedor"
    public Peca(long id, String codigo, String descricao, String tipo, double valorUnitario, int quantidade) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }

    // Métodos utilitários para facilitar a manipulação da lista
    public void adicionarFornecedor(Fornecedor fornecedor) {
        this.fornecedores.add(fornecedor);
    }

    public void removerFornecedor(Fornecedor fornecedor) {
        this.fornecedores.remove(fornecedor);
    }

    // Getters e Setters atualizados

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}