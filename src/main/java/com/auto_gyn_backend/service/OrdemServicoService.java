package com.auto_gyn_backend.service;

import com.auto_gyn_backend.repository.OrdemServicoRepository;
import org.springframework.stereotype.Service;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;

    public OrdemServicoService(OrdemServicoRepository ordemServicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
    }
}
