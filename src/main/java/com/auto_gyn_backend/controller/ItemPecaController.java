package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.dto.ItemPecaDTO;
import com.auto_gyn_backend.dto.OrdemServicoResponseDTO;
import com.auto_gyn_backend.service.OrdemServicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordens-servico/{osId}/pecas") // As rotas são aninhadas na OS
public class ItemPecaController {

    private final OrdemServicoService ordemServicoService;

    public ItemPecaController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    /**
     * Endpoint para ADICIONAR uma peça a uma OS existente.
     */
    @PostMapping
    public ResponseEntity<OrdemServicoResponseDTO> adicionarPeca(@PathVariable Long osId, @RequestBody ItemPecaDTO itemDto) {
        OrdemServicoResponseDTO osAtualizada = ordemServicoService.adicionarPecaEmOS(osId, itemDto);
        return ResponseEntity.ok(osAtualizada);
    }

    /**
     * Endpoint para REMOVER uma peça de uma OS existente.
     */
    @DeleteMapping("/{itemPecaId}")
    public ResponseEntity<Void> removerPeca(@PathVariable Long osId, @PathVariable Long itemPecaId) {
        ordemServicoService.removerPecaDeOS(osId, itemPecaId);
        return ResponseEntity.noContent().build();
    }
}