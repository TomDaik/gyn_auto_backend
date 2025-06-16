package com.auto_gyn_backend.service;

import com.auto_gyn_backend.dto.FuncionarioCreateDTO;
import com.auto_gyn_backend.dto.FuncionarioDTO;
import com.auto_gyn_backend.entity.Funcionario;
import com.auto_gyn_backend.repository.FuncionarioRepository;
import com.auto_gyn_backend.repository.PessoaFisicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PessoaFisicaRepository pessoaFisicaRepository; // Usado para validar a unicidade do CPF

    public FuncionarioService(FuncionarioRepository funcionarioRepository, PessoaFisicaRepository pessoaFisicaRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
    }

    /**
     * Cria um novo funcionário com validações.
     */
    @Transactional
    public FuncionarioDTO save(FuncionarioCreateDTO createDTO) {
        // Validação 1: Algoritmo e formato do CPF
        if (!isCpfValido(createDTO.cpf())) {
            throw new IllegalArgumentException("O CPF informado é inválido.");
        }

        // Validação 2: Unicidade do CPF na base de Pessoas Físicas
        if (pessoaFisicaRepository.existsByCpf(createDTO.cpf())) {
            throw new IllegalArgumentException("O CPF informado já está cadastrado no sistema.");
        }

        // Validação 3: Regra de negócio (salário)
        if (createDTO.salario() < 0) {
            throw new IllegalArgumentException("O salário não pode ser um valor negativo.");
        }

        // Mapeamento do DTO para a Entidade
        Funcionario funcionario = new Funcionario();
        mapDtoToEntity(createDTO, funcionario);

        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

        return toDTO(funcionarioSalvo);
    }

    /**
     * Atualiza um funcionário existente.
     */
    @Transactional
    public void update(Long id, FuncionarioCreateDTO updateDTO) {
        // Validação 1: Algoritmo e formato do CPF
        if (!isCpfValido(updateDTO.cpf())) {
            throw new IllegalArgumentException("O CPF informado é inválido.");
        }

        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário com ID " + id + " não encontrado."));

        // Validação 2: Se o CPF mudou, verifica se o novo já existe
        if (!funcionarioExistente.getCpf().equals(updateDTO.cpf()) && pessoaFisicaRepository.existsByCpf(updateDTO.cpf())) {
            throw new IllegalArgumentException("O novo CPF informado já pertence a outro cadastro.");
        }

        // Validação 3: Regra de negócio (salário)
        if (updateDTO.salario() < 0) {
            throw new IllegalArgumentException("O salário não pode ser um valor negativo.");
        }

        // Mapeia os novos dados para a entidade existente
        mapDtoToEntity(updateDTO, funcionarioExistente);

        funcionarioRepository.save(funcionarioExistente);
    }

    /**
     * Busca todos os funcionários e os retorna como DTOs.
     */
    @Transactional(readOnly = true)
    public List<FuncionarioDTO> findAll() {
        return funcionarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um funcionário pelo ID e o retorna como DTO.
     */
    @Transactional(readOnly = true)
    public FuncionarioDTO findById(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário com ID " + id + " não encontrado."));
        return toDTO(funcionario);
    }

    /**
     * Busca todos os funcionários com um determinado cargo.
     * A busca ignora se o texto do cargo está em maiúsculas ou minúsculas.
     */
    @Transactional(readOnly = true)
    public List<FuncionarioDTO> findByCargo(String cargo) {
        if (cargo == null || cargo.isBlank()) {
            throw new IllegalArgumentException("O cargo não pode ser nulo ou vazio para a busca.");
        }

        // Utilizando o novo método do repositório
        List<Funcionario> funcionarios = funcionarioRepository.findByCargoIgnoreCase(cargo);

        // Mapeando a lista de entidades para uma lista de DTOs
        return funcionarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Deleta um funcionário pelo ID.
     */
    @Transactional
    public void delete(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Funcionário com ID " + id + " não encontrado para exclusão.");
        }
        funcionarioRepository.deleteById(id);
    }

    /**
     * Converte uma entidade Funcionario para FuncionarioDTO.
     */
    private FuncionarioDTO toDTO(Funcionario funcionario) {
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getCargo(),
                funcionario.getSalario(),
                funcionario.getDataNascimento()
        );
    }

    /**
     * Mapeia os dados de um DTO de criação/atualização para uma entidade Funcionario.
     */
    private void mapDtoToEntity(FuncionarioCreateDTO dto, Funcionario entity) {
        // Campos da superclasse PessoaFisica
        entity.setNome(dto.nome());
        entity.setEndereco(dto.endereco());
        entity.setTelefone(dto.telefone());
        entity.setCpf(dto.cpf());
        entity.setDataNascimento(dto.dataNascimento());
        // Campos da classe Funcionario
        entity.setCargo(dto.cargo());
        entity.setSalario(dto.salario());
    }

    /**
     * Valida um número de CPF.
     */
    private boolean isCpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }
            int primeiroDigito = 11 - (soma % 11);
            if (primeiroDigito >= 10) primeiroDigito = 0;
            if ((cpf.charAt(9) - '0') != primeiroDigito) return false;

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }
            int segundoDigito = 11 - (soma % 11);
            if (segundoDigito >= 10) segundoDigito = 0;

            return (cpf.charAt(10) - '0') == segundoDigito;

        } catch (Exception e) {
            return false;
        }
    }
}