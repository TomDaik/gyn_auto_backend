package com.auto_gyn_backend.service;

import com.auto_gyn_backend.repository.FornecedorRepository;
import com.auto_gyn_backend.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }
}
