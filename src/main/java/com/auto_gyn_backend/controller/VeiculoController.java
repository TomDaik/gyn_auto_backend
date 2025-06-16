package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.dto.VeiculoCreateDTO;
import com.auto_gyn_backend.dto.VeiculoResponseDTO;
import com.auto_gyn_backend.service.VeiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos") // Boa prática: usar plural
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> criar(@RequestBody VeiculoCreateDTO createDTO) {
        VeiculoResponseDTO novoVeiculo = veiculoService.save(createDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoVeiculo.idVeiculo()).toUri();
        return ResponseEntity.created(uri).body(novoVeiculo);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(veiculoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veiculoService.findById(id));
    }

    // --- ENDPOINTS DAS SUGESTÕES EXTRAS ---
    @GetMapping("/placa/{placa}")
    public ResponseEntity<VeiculoResponseDTO> buscarPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(veiculoService.findByPlaca(placa));
    }

    @GetMapping("/proprietario/{idProprietario}")
    public ResponseEntity<List<VeiculoResponseDTO>> buscarPorProprietario(@PathVariable Long idProprietario) {
        return ResponseEntity.ok(veiculoService.findAllByProprietario(idProprietario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> atualizar(@PathVariable Long id, @RequestBody VeiculoCreateDTO updateDTO) {
        VeiculoResponseDTO veiculoAtualizado = veiculoService.update(id, updateDTO);
        return ResponseEntity.ok(veiculoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}