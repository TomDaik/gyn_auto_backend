package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.dto.PessoaJuridicaCreateDTO;
import com.auto_gyn_backend.dto.PessoaJuridicaDTO;
import com.auto_gyn_backend.service.PessoaJuridicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pessoas-juridicas")
public class PessoaJuridicaController {

    private final PessoaJuridicaService pessoaJuridicaService;

    // Injeção de dependência via construtor
    public PessoaJuridicaController(PessoaJuridicaService pessoaJuridicaService) {
        this.pessoaJuridicaService = pessoaJuridicaService;
    }

    /**
     * Endpoint para cadastrar uma nova pessoa jurídica.
     * Retorna 201 Created com a localização e os dados do novo recurso.
     */
    @PostMapping
    public ResponseEntity<PessoaJuridicaDTO> criar(@RequestBody PessoaJuridicaCreateDTO createDTO) {
        PessoaJuridicaDTO dtoSalvo = pessoaJuridicaService.save(createDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(dtoSalvo);
    }

    /**
     * Endpoint para listar todas as pessoas jurídicas.
     * Retorna 200 OK com a lista de pessoas jurídicas.
     */
    @GetMapping
    public ResponseEntity<List<PessoaJuridicaDTO>> listarTodos() {
        List<PessoaJuridicaDTO> lista = pessoaJuridicaService.findAll();
        return ResponseEntity.ok(lista);
    }

    /**
     * Endpoint para buscar uma pessoa jurídica pelo ID.
     * Retorna 200 OK com os dados da pessoa jurídica.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PessoaJuridicaDTO> buscarPorId(@PathVariable Long id) {
        PessoaJuridicaDTO dto = pessoaJuridicaService.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint para buscar uma pessoa jurídica pelo CNPJ.
     * Retorna 200 OK com os dados da pessoa jurídica.
     */
    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<PessoaJuridicaDTO> buscarPorCnpj(@PathVariable String cnpj) {
        PessoaJuridicaDTO dto = pessoaJuridicaService.findByCnpj(cnpj);
        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint para atualizar uma pessoa jurídica existente.
     * Retorna 204 No Content em caso de sucesso.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody PessoaJuridicaCreateDTO updateDTO) {
        pessoaJuridicaService.update(id, updateDTO);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para deletar uma pessoa jurídica.
     * Retorna 204 No Content em caso de sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pessoaJuridicaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}