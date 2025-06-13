package com.auto_gyn_backend.service;

import com.auto_gyn_backend.entity.Veiculo;
import com.auto_gyn_backend.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {
    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public Veiculo save(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }

    public Veiculo findById(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veiculo não encontrado"));
    }

    public void atualizar(Veiculo veiculo) {
        veiculoRepository.save(veiculo);
    }

    public void delete(Long id) {
        veiculoRepository.deleteById(id);
    }
}
