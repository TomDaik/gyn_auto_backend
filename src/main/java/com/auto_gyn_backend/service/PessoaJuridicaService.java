package com.auto_gyn_backend.service;

import com.auto_gyn_backend.dto.PessoaJuridicaCreateDTO;
import com.auto_gyn_backend.dto.PessoaJuridicaDTO;
import com.auto_gyn_backend.entity.PessoaJuridica;
import com.auto_gyn_backend.repository.PessoaJuridicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaJuridicaService {

    private final PessoaJuridicaRepository pessoaJuridicaRepository;

    public PessoaJuridicaService(PessoaJuridicaRepository pessoaJuridicaRepository) {
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }

    /**
     * Cria uma nova Pessoa Jurídica com validações de CNPJ.
     */
    @Transactional
    public PessoaJuridicaDTO save(PessoaJuridicaCreateDTO createDTO) {
        // Validação 1: Formato e algoritmo do CNPJ
        if (!isCnpjValido(createDTO.cnpj())) {
            throw new IllegalArgumentException("O CNPJ informado é inválido.");
        }
        // Validação 2: Unicidade do CNPJ no banco de dados
        if (pessoaJuridicaRepository.existsByCnpj(createDTO.cnpj())) {
            throw new IllegalArgumentException("O CNPJ informado já está cadastrado no sistema.");
        }

        PessoaJuridica pessoa = new PessoaJuridica();
        mapDtoToEntity(createDTO, pessoa);

        PessoaJuridica salvo = pessoaJuridicaRepository.save(pessoa);
        return toDTO(salvo);
    }

    /**
     * Atualiza uma Pessoa Jurídica existente.
     */
    @Transactional
    public void update(Long id, PessoaJuridicaCreateDTO updateDTO) {
        if (!isCnpjValido(updateDTO.cnpj())) {
            throw new IllegalArgumentException("O CNPJ informado é inválido.");
        }

        PessoaJuridica pessoaExistente = pessoaJuridicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa Jurídica com ID " + id + " não encontrada."));

        // Valida unicidade apenas se o CNPJ estiver sendo alterado
        if (!pessoaExistente.getCnpj().equals(updateDTO.cnpj()) && pessoaJuridicaRepository.existsByCnpj(updateDTO.cnpj())) {
            throw new IllegalArgumentException("O novo CNPJ informado já pertence a outro cadastro.");
        }

        mapDtoToEntity(updateDTO, pessoaExistente);
        pessoaJuridicaRepository.save(pessoaExistente);
    }

    /**
     * Busca todas as Pessoas Jurídicas e as retorna como uma lista de DTOs.
     */
    @Transactional(readOnly = true)
    public List<PessoaJuridicaDTO> findAll() {
        return pessoaJuridicaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma Pessoa Jurídica pelo ID e a retorna como DTO.
     */
    @Transactional(readOnly = true)
    public PessoaJuridicaDTO findById(Long id) {
        return pessoaJuridicaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa Jurídica com ID " + id + " não encontrada."));
    }

    /**
     * Busca uma Pessoa Jurídica pelo seu CNPJ e a retorna como DTO.
     */
    @Transactional(readOnly = true)
    public PessoaJuridicaDTO findByCnpj(String cnpj) {
        // Remove qualquer formatação do CNPJ para a busca
        String cnpjLimpo = cnpj.replaceAll("[^0-9]", "");

        return pessoaJuridicaRepository.findByCnpj(cnpjLimpo)
                .map(this::toDTO) // Se encontrar, converte para DTO
                .orElseThrow(() -> new IllegalArgumentException("Pessoa Jurídica com CNPJ " + cnpj + " não encontrada."));
    }

    /**
     * Deleta uma Pessoa Jurídica pelo ID.
     */
    @Transactional
    public void delete(Long id) {
        if (!pessoaJuridicaRepository.existsById(id)) {
            throw new IllegalArgumentException("Pessoa Jurídica com ID " + id + " não encontrada para exclusão.");
        }
        pessoaJuridicaRepository.deleteById(id);
    }

    /**
     * Mapeia os dados de um DTO para uma entidade.
     */
    private void mapDtoToEntity(PessoaJuridicaCreateDTO dto, PessoaJuridica entity) {
        entity.setNome(dto.nome());
        entity.setEndereco(dto.endereco());
        entity.setTelefone(dto.telefone());
        entity.setCnpj(dto.cnpj());
        entity.setRazaoSocial(dto.razaoSocial());
    }

    /**
     * Converte uma entidade para um DTO de resposta.
     */
    private PessoaJuridicaDTO toDTO(PessoaJuridica entity) {
        return new PessoaJuridicaDTO(
                entity.getId(),
                entity.getNome(),
                entity.getEndereco(),
                entity.getTelefone(),
                entity.getCnpj(),
                entity.getRazaoSocial()
        );
    }

    /**
     * Valida um número de CNPJ de acordo com o algoritmo oficial.
     */
    private boolean isCnpjValido(String cnpj) {
        if (cnpj == null) return false;

        cnpj = cnpj.replaceAll("[^0-9]", "");
        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] peso = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += (cnpj.charAt(i) - '0') * peso[i];
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 >= 10) digito1 = 0;
            if ((cnpj.charAt(12) - '0') != digito1) return false;

            peso = new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += (cnpj.charAt(i) - '0') * peso[i];
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 >= 10) digito2 = 0;

            return (cnpj.charAt(13) - '0') == digito2;
        } catch (Exception e) {
            return false;
        }
    }
}