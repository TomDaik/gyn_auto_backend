package com.auto_gyn_backend.service;

import com.auto_gyn_backend.entity.Peca;
import com.auto_gyn_backend.repository.PecaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PecaService {

    private final PecaRepository pecaRepository;

    public PecaService(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }

    public Peca save(Peca peca) {
        return pecaRepository.save(peca);
    }

    public List<Peca> findAll() {
        return pecaRepository.findAll();
    }

    public Peca findById(Long id) {
        return pecaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Peça não encontrada"));
    }

    public void atualizar(Peca peca) {
        pecaRepository.save(peca);
    }

    public void delete(Long id) {
        pecaRepository.deleteById(id);
    }
}
