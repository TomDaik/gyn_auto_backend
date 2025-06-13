package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.entity.Veiculo;
import com.auto_gyn_backend.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService VeiculoService;

    @GetMapping
    public List<Veiculo> listarTodos() {
        return VeiculoService.findAll();
    }

    @GetMapping("/{id}")
    public Veiculo buscarPorId(@PathVariable Long id) {
        return VeiculoService.findById(id);
    }

    @PostMapping
    public Veiculo criar(@RequestBody Veiculo veiculo) {
        return VeiculoService.save(veiculo);
    }

    @PutMapping("/{id}")
    public Veiculo atualizar(@RequestBody Veiculo veiculo) {
        return VeiculoService.save(veiculo);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        VeiculoService.delete(id);
    }

}
