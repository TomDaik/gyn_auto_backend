package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.entity.PessoaJuridica;
import com.auto_gyn_backend.service.PessoaJuridicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoa_juridica")
public class PessoaJuridicaController {

    @Autowired
    private PessoaJuridicaService pessoaJuridicaService;

    @GetMapping
    public List<PessoaJuridica> listarTodos() {
        return pessoaJuridicaService.findAll();
    }

    @GetMapping("/{id}")
    public PessoaJuridica buscarPorId(@PathVariable Long id) {
        return pessoaJuridicaService.findById(id);
    }

    @PostMapping
    public PessoaJuridica criar(@RequestBody PessoaJuridica pessoaJuridica) {
        return pessoaJuridicaService.save(pessoaJuridica);
    }

    @PutMapping("/{id}")
    public PessoaJuridica atualizar(@RequestBody PessoaJuridica pessoaJuridica) {
        return pessoaJuridicaService.save(pessoaJuridica);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        pessoaJuridicaService.delete(id);
    }

}
