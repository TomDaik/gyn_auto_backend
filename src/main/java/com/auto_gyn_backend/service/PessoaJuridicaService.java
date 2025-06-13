package com.auto_gyn_backend.service;

import com.auto_gyn_backend.entity.PessoaJuridica;
import com.auto_gyn_backend.repository.PessoaJuridicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaJuridicaService {

    private final PessoaJuridicaRepository pessoaJuridicaRepository;

    public PessoaJuridicaService(PessoaJuridicaRepository pessoaJuridicaRepository) {
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }

    public PessoaJuridica save(PessoaJuridica pessoaJuridica) {
        return pessoaJuridicaRepository.save(pessoaJuridica);
    }

    public List<PessoaJuridica> findAll() {
        return pessoaJuridicaRepository.findAll();
    }

    public PessoaJuridica findById(Long id) {
        return pessoaJuridicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa Juridica não encontrada"));
    }

    public void atualizar(PessoaJuridica pessoaJuridica) {
        pessoaJuridicaRepository.save(pessoaJuridica);
    }

    public void delete(Long id) {
        pessoaJuridicaRepository.deleteById(id);
    }
}
