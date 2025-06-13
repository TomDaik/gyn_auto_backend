package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.entity.Pessoa;
import com.auto_gyn_backend.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> listarTodos() {
        return pessoaService.findAll();
    }

    @GetMapping("/{id}")
    public Pessoa buscarPorId(@PathVariable Long id) {
        return pessoaService.findById(id);
    }

    @PostMapping
    public Pessoa criar(@RequestBody Pessoa pessoa) {
        return pessoaService.save(pessoa);
    }

    @PutMapping("/{id}")
    public Pessoa atualizar(@RequestBody Pessoa pessoa) {
        return pessoaService.save(pessoa);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        pessoaService.delete(id);
    }

}
