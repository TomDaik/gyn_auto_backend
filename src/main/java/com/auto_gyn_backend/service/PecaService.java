package com.auto_gyn_backend.service;

import com.auto_gyn_backend.repository.PecaRepository;
import org.springframework.stereotype.Service;

@Service
public class PecaService {

    private final PecaRepository pecaRepository;

    public PecaService(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }
}
