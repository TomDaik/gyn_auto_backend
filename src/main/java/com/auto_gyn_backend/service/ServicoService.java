package com.auto_gyn_backend.service;

import com.auto_gyn_backend.repository.ServicoRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }
}
