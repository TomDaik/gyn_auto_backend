package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.entity.Fornecedor;
import com.auto_gyn_backend.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    public List<Fornecedor> listarTodos() {
        return fornecedorService.findAll();
    }

    @GetMapping("/{id}")
    public Fornecedor buscarPorId(@PathVariable Long id) {
        return fornecedorService.findById(id);
    }

    @PostMapping
    public Fornecedor criar(@RequestBody Fornecedor fornecedor) {
        return fornecedorService.save(fornecedor);
    }

    @PutMapping("/{id}")
    public Fornecedor atualizar(@RequestBody Fornecedor fornecedor) {
        return fornecedorService.save(fornecedor);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        fornecedorService.delete(id);
    }

}
