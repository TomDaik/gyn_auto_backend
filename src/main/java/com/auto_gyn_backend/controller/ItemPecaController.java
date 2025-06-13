package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.entity.ItemPeca;
import com.auto_gyn_backend.service.ItemPecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item_peca")
public class ItemPecaController {

    @Autowired
    private ItemPecaService itemPecaService;

    @GetMapping
    public List<ItemPeca> listarTodos() {
        return itemPecaService.findAll();
    }

    @GetMapping("/{id}")
    public ItemPeca buscarPorId(@PathVariable Long id) {
        return itemPecaService.findById(id);
    }

    @PostMapping
    public ItemPeca criar(@RequestBody ItemPeca itemPeca) {
        return itemPecaService.save(itemPeca);
    }

    @PutMapping("/{id}")
    public ItemPeca atualizar(@RequestBody ItemPeca itemPeca) {
        return itemPecaService.save(itemPeca);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        itemPecaService.delete(id);
    }

}
