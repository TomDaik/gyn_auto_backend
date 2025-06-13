package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.entity.ItemServico;
import com.auto_gyn_backend.service.ItemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item_servico")
public class ItemServicoController {

    @Autowired
    private ItemServicoService itemServicoService;

    @GetMapping
    public List<ItemServico> listarTodos() {
        return itemServicoService.findAll();
    }

    @GetMapping("/{id}")
    public ItemServico buscarPorId(@PathVariable Long id) {
        return itemServicoService.findById(id);
    }

    @PostMapping
    public ItemServico criar(@RequestBody ItemServico itemServico) {
        return itemServicoService.save(itemServico);
    }

    @PutMapping("/{id}")
    public ItemServico atualizar(@RequestBody ItemServico itemServico) {
        return itemServicoService.save(itemServico);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        itemServicoService.delete(id);
    }

}
