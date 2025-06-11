package com.auto_gyn_backend.service;

import com.auto_gyn_backend.repository.ItemServicoRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemServicoService {

    private final ItemServicoRepository itemServicoRepository;

    public ItemServicoService(ItemServicoRepository itemServicoRepository) {
        this.itemServicoRepository = itemServicoRepository;
    }

}
