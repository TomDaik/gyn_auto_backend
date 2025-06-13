package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.entity.Funcionario;
import com.auto_gyn_backend.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public List<Funcionario> listarTodos() {
        return funcionarioService.findAll();
    }

    @GetMapping("/{id}")
    public Funcionario buscarPorId(@PathVariable Long id) {
        return funcionarioService.findById(id);
    }

    @PostMapping
    public Funcionario criar(@RequestBody Funcionario funcionario) {
        return funcionarioService.save(funcionario);
    }

    @PutMapping("/{id}")
    public Funcionario atualizar(@RequestBody Funcionario funcionario) {
        return funcionarioService.save(funcionario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        funcionarioService.delete(id);
    }

}
