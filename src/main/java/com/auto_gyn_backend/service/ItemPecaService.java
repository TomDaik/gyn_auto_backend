package com.auto_gyn_backend.service;


import com.auto_gyn_backend.repository.ItemPecaRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemPecaService {

    private final ItemPecaRepository itemPecaRepository;

    public ItemPecaService(ItemPecaRepository itemPecaRepository) {
        this.itemPecaRepository = itemPecaRepository;
    }

}
