package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.dto.OrdemServicoCreateDTO;
import com.auto_gyn_backend.dto.OrdemServicoResponseDTO;
import com.auto_gyn_backend.service.OrdemServicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    /**
     * Endpoint para criar uma nova Ordem de Serviço completa.
     * Recebe todos os dados, incluindo itens, e orquestra a criação.
     */
    @PostMapping
    public ResponseEntity<OrdemServicoResponseDTO> criar(@RequestBody OrdemServicoCreateDTO createDTO) {
        OrdemServicoResponseDTO novaOS = ordemServicoService.criarOS(createDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novaOS.id()).toUri();
        return ResponseEntity.created(uri).body(novaOS);
    }

    /**
     * Endpoint para buscar uma Ordem de Serviço detalhada pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDTO> buscarPorId(@PathVariable Long id) {
        OrdemServicoResponseDTO os = ordemServicoService.findDetailedById(id);
        return ResponseEntity.ok(os);
    }

    /**
     * Endpoint para listar todas as Ordens de Serviço (em formato resumido).
     */
    @GetMapping
    public ResponseEntity<List<OrdemServicoResponseDTO>> listarTodas() {
        // SUGESTÃO: Criar um DTO mais simples para listagem (ex: OrdemServicoListDTO)
        // Por simplicidade, reutilizaremos o DTO de resposta detalhado aqui.
        List<OrdemServicoResponseDTO> listaOS = ordemServicoService.findAllDetailed();
        return ResponseEntity.ok(listaOS);
    }

    /**
     * Endpoint para alterar o status de uma OS para PAGO.
     * Representa a finalização e o pagamento da OS.
     */
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizarOS(@PathVariable Long id) {
        ordemServicoService.finalizarOS(id);
        return ResponseEntity.ok().build(); // Retorna 200 OK
    }

    /**
     * Endpoint para alterar o status de uma OS para CANCELADO.
     * Pode, opcionalmente, estornar peças ao estoque.
     */
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarOS(@PathVariable Long id) {
        ordemServicoService.cancelarOS(id);
        return ResponseEntity.ok().build(); // Retorna 200 OK
    }

    /**
     * Endpoint para deletar uma Ordem de Serviço.
     * ATENÇÃO: Em sistemas reais, a exclusão física de uma OS é rara.
     * Geralmente, o cancelamento é a operação preferida para manter o histórico.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ordemServicoService.delete(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}