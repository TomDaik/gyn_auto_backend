package com.auto_gyn_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "item_peca")
public class ItemPeca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_os")
    private OrdemServico ordemServico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_peca")
    private Peca peca; // A peça do catálogo/estoque

    @Column(nullable = false)
    private int quantidade; // A quantidade de peças utilizadas na OS

    @Column(name = "valor_unitario_cobrado", nullable = false)
    private double valorUnitarioCobrado; // O preço da peça no momento da OS

    public ItemPeca() {
    }

    public ItemPeca(OrdemServico ordemServico, Peca peca, int quantidade) {
        this.ordemServico = ordemServico;
        this.peca = peca;
        this.quantidade = quantidade;
        if (peca != null) {
            this.valorUnitarioCobrado = peca.getValorUnitario();
        }
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitarioCobrado() {
        return valorUnitarioCobrado;
    }

    public void setValorUnitarioCobrado(double valorUnitarioCobrado) {
        this.valorUnitarioCobrado = valorUnitarioCobrado;
    }
}