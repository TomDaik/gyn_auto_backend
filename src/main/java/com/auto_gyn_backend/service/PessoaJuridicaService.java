package com.auto_gyn_backend.service;

import com.auto_gyn_backend.repository.PessoaJuridicaRepository;
import org.springframework.stereotype.Service;

@Service
public class PessoaJuridicaService {

    private final PessoaJuridicaRepository pessoaJuridicaRepository;

    public PessoaJuridicaService(PessoaJuridicaRepository pessoaJuridicaRepository) {
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }
}
