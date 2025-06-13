package com.auto_gyn_backend.service;

import com.auto_gyn_backend.entity.ItemServico;
import com.auto_gyn_backend.repository.ItemServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServicoService {

    private final ItemServicoRepository itemServicoRepository;

    public ItemServicoService(ItemServicoRepository itemServicoRepository) {
        this.itemServicoRepository = itemServicoRepository;
    }

    public ItemServico save(ItemServico itemServico) {
        return itemServicoRepository.save(itemServico);
    }

    public List<ItemServico> findAll() {
        return itemServicoRepository.findAll();
    }

    public ItemServico findById(Long id) {
        return itemServicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item Servico não encontrada"));
    }

    public void atualizar(ItemServico itemServico) {
        itemServicoRepository.save(itemServico);
    }

    public void delete(Long id) {
        itemServicoRepository.deleteById(id);
    }
}
