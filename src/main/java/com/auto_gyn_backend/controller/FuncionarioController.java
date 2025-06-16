package com.auto_gyn_backend.controller;

import com.auto_gyn_backend.dto.FuncionarioCreateDTO;
import com.auto_gyn_backend.dto.FuncionarioDTO;
import com.auto_gyn_backend.service.FuncionarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/funcionarios") // Usando plural, como é boa prática
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    // Injeção de dependência via construtor
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    /**
     * Endpoint para cadastrar um novo funcionário.
     * Retorna 201 Created com a localização e os dados do novo funcionário.
     */
    @PostMapping
    public ResponseEntity<FuncionarioDTO> criar(@RequestBody FuncionarioCreateDTO createDTO) {
        FuncionarioDTO novoFuncionario = funcionarioService.save(createDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoFuncionario.id()).toUri();
        return ResponseEntity.created(uri).body(novoFuncionario);
    }

    /**
     * Endpoint para listar todos os funcionários cadastrados.
     * Retorna 200 OK com a lista de funcionários.
     */
    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> listarTodos() {
        List<FuncionarioDTO> funcionarios = funcionarioService.findAll();
        return ResponseEntity.ok(funcionarios);
    }

    /**
     * Endpoint para buscar um funcionário pelo seu ID.
     * Retorna 200 OK com os dados do funcionário.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> buscarPorId(@PathVariable Long id) {
        FuncionarioDTO funcionario = funcionarioService.findById(id);
        return ResponseEntity.ok(funcionario);
    }

    /**
     * Endpoint para buscar funcionários por cargo.
     * Exemplo de uso: GET /api/funcionarios/buscar?cargo=Mecanico
     * Retorna 200 OK com a lista de funcionários encontrados.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<FuncionarioDTO>> buscarPorCargo(@RequestParam String cargo) {
        List<FuncionarioDTO> funcionarios = funcionarioService.findByCargo(cargo);
        return ResponseEntity.ok(funcionarios);
    }

    /**
     * Endpoint para atualizar um funcionário existente.
     * Retorna 204 No Content em caso de sucesso.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody FuncionarioCreateDTO updateDTO) {
        funcionarioService.update(id, updateDTO);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para deletar um funcionário.
     * Retorna 204 No Content em caso de sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}