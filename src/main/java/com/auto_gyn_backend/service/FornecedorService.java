package com.auto_gyn_backend.service;

import com.auto_gyn_backend.entity.Fornecedor;
import com.auto_gyn_backend.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public Fornecedor save(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public List<Fornecedor> findAll() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor findById(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));
    }

    public void atualizar(Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
    }

    public void delete(Long id) {
        fornecedorRepository.deleteById(id);
    }
}
