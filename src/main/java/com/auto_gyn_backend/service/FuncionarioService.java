package com.auto_gyn_backend.service;

import com.auto_gyn_backend.entity.Funcionario;
import com.auto_gyn_backend.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Funcionario save(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionario não encontrado"));
    }

    public void atualizar(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
    }

    public void delete(Long id) {
        funcionarioRepository.deleteById(id);
    }
}
