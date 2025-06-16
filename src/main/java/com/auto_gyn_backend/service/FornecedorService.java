package com.auto_gyn_backend.service;

import com.auto_gyn_backend.dto.FornecedorCreateDTO;
import com.auto_gyn_backend.dto.FornecedorDTO;
import com.auto_gyn_backend.entity.Fornecedor;
import com.auto_gyn_backend.repository.FornecedorRepository;
import com.auto_gyn_backend.repository.PessoaJuridicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final PessoaJuridicaRepository pessoaJuridicaRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository, PessoaJuridicaRepository pessoaJuridicaRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }

    @Transactional
    public FornecedorDTO save(FornecedorCreateDTO createDTO) {
        if (!isCnpjValido(createDTO.cnpj())) {
            throw new IllegalArgumentException("O CNPJ informado é inválido.");
        }
        if (pessoaJuridicaRepository.existsByCnpj(createDTO.cnpj())) {
            throw new IllegalArgumentException("O CNPJ informado já está cadastrado.");
        }

        Fornecedor fornecedor = new Fornecedor();
        mapDtoToEntity(createDTO, fornecedor);

        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
        return toDTO(fornecedorSalvo);
    }

    @Transactional
    public void update(Long id, FornecedorCreateDTO updateDTO) {
        if (!isCnpjValido(updateDTO.cnpj())) {
            throw new IllegalArgumentException("O CNPJ informado é inválido.");
        }

        Fornecedor fornecedorExistente = fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor com ID " + id + " não encontrado."));

        if (!fornecedorExistente.getCnpj().equals(updateDTO.cnpj()) && pessoaJuridicaRepository.existsByCnpj(updateDTO.cnpj())) {
            throw new IllegalArgumentException("O novo CNPJ informado já pertence a outro cadastro.");
        }

        mapDtoToEntity(updateDTO, fornecedorExistente);
        fornecedorRepository.save(fornecedorExistente);
    }

    @Transactional(readOnly = true)
    public List<FornecedorDTO> findAll() {
        return fornecedorRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FornecedorDTO findById(Long id) {
        return fornecedorRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor com ID " + id + " não encontrado."));
    }

    @Transactional
    public void delete(Long id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new IllegalArgumentException("Fornecedor com ID " + id + " não encontrado para exclusão.");
        }
        fornecedorRepository.deleteById(id);
    }

    private void mapDtoToEntity(FornecedorCreateDTO dto, Fornecedor entity) {
        entity.setNome(dto.nome());
        entity.setEndereco(dto.endereco());
        entity.setTelefone(dto.telefone());
        entity.setCnpj(dto.cnpj());
        entity.setRazaoSocial(dto.razaoSocial());
    }

    private FornecedorDTO toDTO(Fornecedor entity) {
        return new FornecedorDTO(entity.getId(), entity.getNome(), entity.getEndereco(), entity.getTelefone(), entity.getCnpj(), entity.getRazaoSocial());
    }

    private boolean isCnpjValido(String cnpj) {
        cnpj = cnpj.replaceAll("[^0-9]", "");
        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) return false;
        try {
            int[] peso = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 12; i++) soma += (cnpj.charAt(i) - '0') * peso[i];
            int digito1 = 11 - (soma % 11);
            if (digito1 >= 10) digito1 = 0;
            if ((cnpj.charAt(12) - '0') != digito1) return false;

            peso = new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            soma = 0;
            for (int i = 0; i < 13; i++) soma += (cnpj.charAt(i) - '0') * peso[i];
            int digito2 = 11 - (soma % 11);
            if (digito2 >= 10) digito2 = 0;
            return (cnpj.charAt(13) - '0') == digito2;
        } catch (Exception e) {
            return false;
        }
    }
}