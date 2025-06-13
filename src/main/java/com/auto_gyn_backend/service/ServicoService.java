package com.auto_gyn_backend.service;

import com.auto_gyn_backend.entity.Servico;
import com.auto_gyn_backend.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public Servico save(Servico servico) {
        return servicoRepository.save(servico);
    }

    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }

    public Servico findById(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
    }

    public void atualizar(Servico servico) {
        servicoRepository.save(servico);
    }

    public void delete(Long id) {
        servicoRepository.deleteById(id);
    }
}
