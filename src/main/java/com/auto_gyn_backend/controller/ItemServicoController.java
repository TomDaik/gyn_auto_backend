package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.dto.ItemServicoDTO; // <<< MUDANÇA AQUI
import com.auto_gyn_backend.dto.OrdemServicoResponseDTO;
import com.auto_gyn_backend.service.OrdemServicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordens-servico/{osId}/servicos")
public class ItemServicoController {

    private final OrdemServicoService ordemServicoService;

    public ItemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    /**
     * Endpoint para ADICIONAR um serviço a uma OS existente.
     */
    @PostMapping
    public ResponseEntity<OrdemServicoResponseDTO> adicionarServico(@PathVariable Long osId, @RequestBody ItemServicoDTO itemDto) { // <<< MUDANÇA AQUI
        OrdemServicoResponseDTO osAtualizada = ordemServicoService.adicionarServicoEmOS(osId, itemDto);
        return ResponseEntity.ok(osAtualizada);
    }

    /**
     * Endpoint para REMOVER um serviço de uma OS existente.
     */
    @DeleteMapping("/{itemServicoId}")
    public ResponseEntity<Void> removerServico(@PathVariable Long osId, @PathVariable Long itemServicoId) {
        ordemServicoService.removerServicoDeOS(osId, itemServicoId);
        return ResponseEntity.noContent().build();
    }
}