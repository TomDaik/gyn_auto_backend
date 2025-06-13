package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.dto.PessoaFisicaCreateDTO;
import com.auto_gyn_backend.dto.PessoaFisicaDTO;
import com.auto_gyn_backend.service.PessoaFisicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pessoas_fisicas")
public class PessoaFisicaController {

    private final PessoaFisicaService pessoaFisicaService;

    public PessoaFisicaController(PessoaFisicaService pessoaFisicaService) {
        this.pessoaFisicaService = pessoaFisicaService;
    }

    @GetMapping
    public ResponseEntity<List<PessoaFisicaDTO>> listarTodos() {
        List<PessoaFisicaDTO> list = pessoaFisicaService.findAll();
        return ResponseEntity.ok(list); // Retorna 200 OK com a lista no corpo
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaFisicaDTO> buscarPorId(@PathVariable Long id) {
        PessoaFisicaDTO dto = pessoaFisicaService.findById(id);
        return ResponseEntity.ok(dto); // Retorna 200 OK com o objeto encontrado
    }

    @PostMapping
    public ResponseEntity<PessoaFisicaDTO> criar(@RequestBody PessoaFisicaCreateDTO createDTO) {
        PessoaFisicaDTO dtoSalvo = pessoaFisicaService.save(createDTO);

        // Cria a URI do novo recurso criado para retornar no cabeçalho "Location"
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoSalvo.id()).toUri();

        return ResponseEntity.created(uri).body(dtoSalvo); // Retorna 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody PessoaFisicaCreateDTO updateDTO) {
        pessoaFisicaService.atualizar(id, updateDTO);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content, indicando sucesso sem corpo de resposta
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pessoaFisicaService.delete(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}