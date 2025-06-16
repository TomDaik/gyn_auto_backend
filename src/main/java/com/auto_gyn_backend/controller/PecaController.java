package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.dto.PecaCreateDTO;
import com.auto_gyn_backend.dto.PecaResponseDTO;
import com.auto_gyn_backend.service.PecaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pecas") // Usando plural, como é boa prática
public class PecaController {

    private final PecaService pecaService;

    // Injeção de dependência via construtor
    public PecaController(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    /**
     * Endpoint para cadastrar uma nova peça.
     * Retorna 201 Created com a localização e os dados da nova peça.
     */
    @PostMapping
    public ResponseEntity<PecaResponseDTO> criar(@RequestBody PecaCreateDTO createDTO) {
        PecaResponseDTO novaPeca = pecaService.save(createDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novaPeca.id()).toUri();
        return ResponseEntity.created(uri).body(novaPeca);
    }

    /**
     * Endpoint para listar todas as peças cadastradas.
     * Retorna 200 OK com a lista de peças.
     */
    @GetMapping
    public ResponseEntity<List<PecaResponseDTO>> listarTodos() {
        List<PecaResponseDTO> pecas = pecaService.findAll();
        return ResponseEntity.ok(pecas);
    }

    /**
     * Endpoint para buscar uma peça específica pelo seu ID.
     * Retorna 200 OK com os dados da peça.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PecaResponseDTO> buscarPorId(@PathVariable Long id) {
        PecaResponseDTO peca = pecaService.findById(id);
        return ResponseEntity.ok(peca);
    }

    /**
     * Endpoint para atualizar uma peça existente.
     * Retorna 204 No Content em caso de sucesso.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody PecaCreateDTO updateDTO) {
        pecaService.update(id, updateDTO);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para deletar uma peça.
     * Retorna 204 No Content em caso de sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pecaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}