package com.auto_gyn_backend.service;

import com.auto_gyn_backend.repository.FornecedorRepository;
import com.auto_gyn_backend.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

}
