package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.entity.Servico;
import com.auto_gyn_backend.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servico")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @GetMapping
    public List<Servico> listarTodos() {
        return servicoService.findAll();
    }

    @GetMapping("/{id}")
    public Servico buscarPorId(@PathVariable Long id) {
        return servicoService.findById(id);
    }

    @PostMapping
    public Servico criar(@RequestBody Servico servico) {
        return servicoService.save(servico);
    }

    @PutMapping("/{id}")
    public Servico atualizar(@RequestBody Servico servico) {
        return servicoService.save(servico);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        servicoService.delete(id);
    }

}
