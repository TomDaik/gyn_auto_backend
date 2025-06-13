package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.entity.OrdemServico;
import com.auto_gyn_backend.service.OrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ordem_servico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemServicoService;

    @GetMapping
    public List<OrdemServico> listarTodos() {
        return ordemServicoService.findAll();
    }

    @GetMapping("/{id}")
    public OrdemServico buscarPorId(@PathVariable Long id) {
        return ordemServicoService.findById(id);
    }

    @PostMapping
    public OrdemServico criar(@RequestBody OrdemServico ordemServico) {
        return ordemServicoService.save(ordemServico);
    }

    @PutMapping("/{id}")
    public OrdemServico atualizar(@RequestBody OrdemServico ordemServico) {
        return ordemServicoService.save(ordemServico);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        ordemServicoService.delete(id);
    }

}
