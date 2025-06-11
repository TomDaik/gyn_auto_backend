package com.auto_gyn_backend.service;

import com.auto_gyn_backend.entity.Veiculo;
import com.auto_gyn_backend.repository.PessoaRepository;
import com.auto_gyn_backend.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {
    private final VeiculoRepository veiculoRepository;
    private final PessoaRepository pessoaRepository;

    public VeiculoService(VeiculoRepository veiculoRepository, PessoaRepository pessoaRepository) {
        this.veiculoRepository = veiculoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }

    public Optional<Veiculo> findById(int id) {
        return veiculoRepository.findById(id);
    }

    public Veiculo save(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    public void delete(int id) {
        veiculoRepository.deleteById(id);
    }

}
