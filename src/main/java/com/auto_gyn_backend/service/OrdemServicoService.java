package com.auto_gyn_backend.service;

import com.auto_gyn_backend.entity.OrdemServico;
import com.auto_gyn_backend.repository.OrdemServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;

    public OrdemServicoService(OrdemServicoRepository ordemServicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
    }

    public OrdemServico save(OrdemServico ordemServico) {
        return ordemServicoRepository.save(ordemServico);
    }

    public List<OrdemServico> findAll() {
        return ordemServicoRepository.findAll();
    }

    public OrdemServico findById(Long id) {
        return ordemServicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de serviço não encontrada"));
    }

    public void atualizar(OrdemServico ordemServico) {
        ordemServicoRepository.save(ordemServico);
    }

    public void delete(Long id) {
        ordemServicoRepository.deleteById(id);
    }
}
