package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.dto.ServicoCreateDTO;
import com.auto_gyn_backend.dto.ServicoDTO;
import com.auto_gyn_backend.service.ServicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    /**
     * Endpoint para cadastrar um novo serviço.
     * Retorna 201 Created com a localização e os dados do novo serviço.
     */
    @PostMapping
    public ResponseEntity<ServicoDTO> criar(@RequestBody ServicoCreateDTO createDTO) {
        ServicoDTO dtoSalvo = servicoService.save(createDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(dtoSalvo);
    }

    /**
     * Endpoint para listar todos os serviços cadastrados.
     * Retorna 200 OK com a lista de serviços.
     */
    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listarTodos() {
        List<ServicoDTO> lista = servicoService.findAll();
        return ResponseEntity.ok(lista);
    }

    /**
     * Endpoint para buscar um serviço pelo seu ID.
     * Retorna 200 OK com os dados do serviço.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscarPorId(@PathVariable Long id) {
        ServicoDTO dto = servicoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint para atualizar um serviço existente.
     * Retorna 204 No Content em caso de sucesso.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody ServicoCreateDTO updateDTO) {
        servicoService.update(id, updateDTO);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para deletar um serviço.
     * Retorna 204 No Content em caso de sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servicoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}