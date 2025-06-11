package com.auto_gyn_backend.service;

import com.auto_gyn_backend.repository.PessoaFisicaRepository;
import org.springframework.stereotype.Service;

@Service
public class PessoaFisicaService {

    private final PessoaFisicaRepository pessoaFisicaRepository;

    public PessoaFisicaService(PessoaFisicaRepository pessoaFisicaRepository) {
        this.pessoaFisicaRepository = pessoaFisicaRepository;
    }
}
