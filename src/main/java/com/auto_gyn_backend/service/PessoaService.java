package com.auto_gyn_backend.service;

import com.auto_gyn_backend.entity.Pessoa;
import com.auto_gyn_backend.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Pessoa findById(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrado"));
    }

    public void atualizar(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
    }

    public void delete(Long id) {
        pessoaRepository.deleteById(id);
    }
}
