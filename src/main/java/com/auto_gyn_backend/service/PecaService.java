package com.auto_gyn_backend.service;

import com.auto_gyn_backend.dto.FornecedorDTO;
import com.auto_gyn_backend.dto.PecaCreateDTO;
import com.auto_gyn_backend.dto.PecaResponseDTO;
import com.auto_gyn_backend.entity.Fornecedor;
import com.auto_gyn_backend.entity.Peca;
import com.auto_gyn_backend.repository.FornecedorRepository;
import com.auto_gyn_backend.repository.PecaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PecaService {

    private final PecaRepository pecaRepository;
    private final FornecedorRepository fornecedorRepository;

    public PecaService(PecaRepository pecaRepository, FornecedorRepository fornecedorRepository) {
        this.pecaRepository = pecaRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public PecaResponseDTO save(PecaCreateDTO createDTO) {
        if (pecaRepository.existsByCodigoIgnoreCase(createDTO.codigo())) {
            throw new IllegalArgumentException("Já existe uma peça com o código " + createDTO.codigo());
        }

        Peca peca = new Peca();
        mapDtoToEntity(createDTO, peca);

        Peca pecaSalva = pecaRepository.save(peca);
        return toDTO(pecaSalva);
    }

    @Transactional
    public void update(Long id, PecaCreateDTO updateDTO) {
        Peca pecaExistente = pecaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Peça com ID " + id + " não encontrada."));

        // Valida se o código está sendo alterado para um que já existe
        if (!pecaExistente.getCodigo().equalsIgnoreCase(updateDTO.codigo())) {
            pecaRepository.findByCodigoIgnoreCase(updateDTO.codigo()).ifPresent(outraPeca -> {
                if (outraPeca.getId() != id) {
                    throw new IllegalArgumentException("O novo código " + updateDTO.codigo() + " já pertence a outra peça.");
                }
            });
        }

        mapDtoToEntity(updateDTO, pecaExistente);
        pecaRepository.save(pecaExistente);
    }

    @Transactional(readOnly = true)
    public List<PecaResponseDTO> findAll() {
        return pecaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PecaResponseDTO findById(Long id) {
        return pecaRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Peça com ID " + id + " não encontrada."));
    }

    @Transactional
    public void delete(Long id) {
        if (!pecaRepository.existsById(id)) {
            throw new IllegalArgumentException("Peça com ID " + id + " não encontrada para exclusão.");
        }
        pecaRepository.deleteById(id);
    }

    private void mapDtoToEntity(PecaCreateDTO dto, Peca entity) {
        entity.setCodigo(dto.codigo());
        entity.setDescricao(dto.descricao());
        entity.setTipo(dto.tipo());
        entity.setValorUnitario(dto.valorUnitario());
        entity.setQuantidade(dto.quantidade());

        // Orquestração: Busca os fornecedores pelos IDs e os associa à peça
        if (dto.idsFornecedores() != null) {
            List<Fornecedor> fornecedores = new ArrayList<>();
            for (Long idFornecedor : dto.idsFornecedores()) {
                Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor)
                        .orElseThrow(() -> new IllegalArgumentException("Fornecedor com ID " + idFornecedor + " não encontrado."));
                fornecedores.add(fornecedor);
            }
            entity.setFornecedores(fornecedores);
        }
    }

    private PecaResponseDTO toDTO(Peca entity) {
        List<FornecedorDTO> fornecedorDTOs = entity.getFornecedores().stream()
                .map(f -> new FornecedorDTO(f.getId(), f.getNome(), f.getEndereco(), f.getTelefone(), f.getCnpj(), f.getRazaoSocial()))
                .collect(Collectors.toList());

        return new PecaResponseDTO(
                entity.getId(),
                entity.getCodigo(),
                entity.getDescricao(),
                entity.getTipo(),
                entity.getValorUnitario(),
                entity.getQuantidade(),
                fornecedorDTOs
        );
    }
}