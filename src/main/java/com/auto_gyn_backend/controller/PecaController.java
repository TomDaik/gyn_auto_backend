package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.entity.Peca;
import com.auto_gyn_backend.service.PecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peca")
public class PecaController {

    @Autowired
    private PecaService pecaService;

    @GetMapping
    public List<Peca> listarTodos() {
        return pecaService.findAll();
    }

    @GetMapping("/{id}")
    public Peca buscarPorId(@PathVariable Long id) {
        return pecaService.findById(id);
    }

    @PostMapping
    public Peca criar(@RequestBody Peca peca) {
        return pecaService.save(peca);
    }

    @PutMapping("/{id}")
    public Peca atualizar(@RequestBody Peca peca) {
        return pecaService.save(peca);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        pecaService.delete(id);
    }

}
