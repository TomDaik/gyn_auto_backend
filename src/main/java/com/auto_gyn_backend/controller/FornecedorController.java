package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.dto.FornecedorCreateDTO;
import com.auto_gyn_backend.dto.FornecedorDTO;
import com.auto_gyn_backend.service.FornecedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/fornecedores") // Usando plural, como é boa prática
public class FornecedorController {

    private final FornecedorService fornecedorService;

    // Injeção de dependência via construtor
    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    /**
     * Endpoint para criar um novo fornecedor.
     * Retorna 201 Created com a localização do novo recurso.
     */
    @PostMapping
    public ResponseEntity<FornecedorDTO> criar(@RequestBody FornecedorCreateDTO createDTO) {
        FornecedorDTO novoFornecedor = fornecedorService.save(createDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoFornecedor.id()).toUri();
        return ResponseEntity.created(uri).body(novoFornecedor);
    }

    /**
     * Endpoint para listar todos os fornecedores.
     * Retorna 200 OK com a lista de fornecedores.
     */
    @GetMapping
    public ResponseEntity<List<FornecedorDTO>> listarTodos() {
        List<FornecedorDTO> fornecedores = fornecedorService.findAll();
        return ResponseEntity.ok(fornecedores);
    }

    /**
     * Endpoint para buscar um fornecedor pelo seu ID.
     * Retorna 200 OK com os dados do fornecedor.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDTO> buscarPorId(@PathVariable Long id) {
        FornecedorDTO fornecedor = fornecedorService.findById(id);
        return ResponseEntity.ok(fornecedor);
    }

    /**
     * Endpoint para atualizar um fornecedor existente.
     * Retorna 204 No Content em caso de sucesso.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody FornecedorCreateDTO updateDTO) {
        fornecedorService.update(id, updateDTO);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para deletar um fornecedor.
     * Retorna 204 No Content em caso de sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        fornecedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}