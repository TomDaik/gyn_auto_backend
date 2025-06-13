package com.auto_gyn_backend.service;


import com.auto_gyn_backend.entity.ItemPeca;
import com.auto_gyn_backend.repository.ItemPecaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPecaService {

    private final ItemPecaRepository itemPecaRepository;

    public ItemPecaService(ItemPecaRepository itemPecaRepository) {
        this.itemPecaRepository = itemPecaRepository;
    }

    public ItemPeca save(ItemPeca itemPeca) {
        return itemPecaRepository.save(itemPeca);
    }

    public List<ItemPeca> findAll() {
        return itemPecaRepository.findAll();
    }

    public ItemPeca findById(Long id) {
        return itemPecaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item Peça não encontrada"));
    }

    public void atualizar(ItemPeca itemPeca) {
        itemPecaRepository.save(itemPeca);
    }

    public void delete(Long id) {
        itemPecaRepository.deleteById(id);
    }
}
