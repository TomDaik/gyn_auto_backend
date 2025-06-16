package com.auto_gyn_backend.service;

import com.auto_gyn_backend.dto.PessoaFisicaCreateDTO;
import com.auto_gyn_backend.dto.PessoaFisicaDTO;
import com.auto_gyn_backend.entity.PessoaFisica;
import com.auto_gyn_backend.repository.PessoaFisicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaFisicaService {

    private final PessoaFisicaRepository pessoaFisicaRepository;

    public PessoaFisicaService(PessoaFisicaRepository pessoaFisicaRepository) {
        this.pessoaFisicaRepository = pessoaFisicaRepository;
    }

    @Transactional
    public PessoaFisicaDTO save(PessoaFisicaCreateDTO createDTO) {
        // PASSO 1: VALIDAÇÃO DE FORMATO E ALGORITMO COM A FUNÇÃO INTERNA
        if (!isCpfValido(createDTO.cpf())) {
            throw new IllegalArgumentException("O CPF informado é inválido.");
        }

        // PASSO 2: VALIDAÇÃO DE UNICIDADE NO BANCO DE DADOS
        if (pessoaFisicaRepository.existsByCpf(createDTO.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema.");
        }

        PessoaFisica pessoa = new PessoaFisica();
        pessoa.setNome(createDTO.nome());
        pessoa.setEndereco(createDTO.endereco());
        pessoa.setTelefone(createDTO.telefone());
        pessoa.setCpf(createDTO.cpf());
        pessoa.setDataNascimento(createDTO.dataNascimento());

        PessoaFisica pessoaSalva = pessoaFisicaRepository.save(pessoa);

        return toDTO(pessoaSalva);
    }

    @Transactional
    public void atualizar(Long id, PessoaFisicaCreateDTO updateDTO) {
        // VALIDAÇÃO DO CPF ANTES DE QUALQUER COISA
        if (!isCpfValido(updateDTO.cpf())) {
            throw new IllegalArgumentException("O CPF informado é inválido.");
        }

        PessoaFisica pessoaExistente = pessoaFisicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa Física não encontrada com ID: " + id));

        if (!pessoaExistente.getCpf().equals(updateDTO.cpf()) && pessoaFisicaRepository.existsByCpf(updateDTO.cpf())) {
            throw new IllegalArgumentException("O novo CPF informado já pertence a outro cadastro.");
        }

        pessoaExistente.setNome(updateDTO.nome());
        pessoaExistente.setEndereco(updateDTO.endereco());
        pessoaExistente.setTelefone(updateDTO.telefone());
        pessoaExistente.setCpf(updateDTO.cpf());
        pessoaExistente.setDataNascimento(updateDTO.dataNascimento());

        pessoaFisicaRepository.save(pessoaExistente);
    }

    @Transactional(readOnly = true)
    public List<PessoaFisicaDTO> findAll() {
        return pessoaFisicaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PessoaFisicaDTO findById(Long id) {
        PessoaFisica pessoa = pessoaFisicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa Física não encontrada com ID: " + id));
        return toDTO(pessoa);
    }

    /**
     * Busca uma Pessoa Física pelo seu CPF.
     */
    @Transactional(readOnly = true)
    public PessoaFisicaDTO findByCpf(String cpf) {
        // Remove qualquer formatação do CPF para a busca
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        return pessoaFisicaRepository.findByCpf(cpfLimpo)
                .map(this::toDTO) // Se encontrar, converte para DTO
                .orElseThrow(() -> new IllegalArgumentException("Pessoa Física com CPF " + cpf + " não encontrada."));
    }

    @Transactional
    public void delete(Long id) {
        if (!pessoaFisicaRepository.existsById(id)) {
            throw new IllegalArgumentException("Pessoa Física não encontrada com ID: " + id);
        }
        pessoaFisicaRepository.deleteById(id);
    }

    private PessoaFisicaDTO toDTO(PessoaFisica pessoaFisica) {
        return new PessoaFisicaDTO(
                pessoaFisica.getId(),
                pessoaFisica.getNome(),
                pessoaFisica.getEndereco(),
                pessoaFisica.getTelefone(),
                pessoaFisica.getCpf(),
                pessoaFisica.getDataNascimento()
        );
    }

    private boolean isCpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }

        // 1. Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // 2. Garante que o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // 3. Verifica se todos os dígitos são iguais, o que é inválido
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // ---- Cálculo do 1º Dígito Verificador ----
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }

            int primeiroDigito = 11 - (soma % 11);
            if (primeiroDigito >= 10) {
                primeiroDigito = 0;
            }

            // 4. Compara o 1º dígito calculado com o dígito real do CPF
            if ((cpf.charAt(9) - '0') != primeiroDigito) {
                return false;
            }

            // ---- Cálculo do 2º Dígito Verificador ----
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }

            int segundoDigito = 11 - (soma % 11);
            if (segundoDigito >= 10) {
                segundoDigito = 0;
            }

            // 5. Compara o 2º dígito calculado com o dígito real do CPF
            return (cpf.charAt(10) - '0') == segundoDigito;

        } catch (Exception e) {
            return false;
        }
    }
}