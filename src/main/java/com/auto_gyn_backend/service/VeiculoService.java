package com.auto_gyn_backend.service;

import com.auto_gyn_backend.dto.ProprietarioDTO;
import com.auto_gyn_backend.dto.VeiculoCreateDTO;
import com.auto_gyn_backend.dto.VeiculoResponseDTO;
import com.auto_gyn_backend.entity.PessoaFisica;
import com.auto_gyn_backend.entity.Veiculo;
import com.auto_gyn_backend.repository.PessoaFisicaRepository;
import com.auto_gyn_backend.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final PessoaFisicaRepository pessoaFisicaRepository; // Precisamos dele para buscar o proprietário

    public VeiculoService(VeiculoRepository veiculoRepository, PessoaFisicaRepository pessoaFisicaRepository) {
        this.veiculoRepository = veiculoRepository;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
    }

    @Transactional
    public VeiculoResponseDTO save(VeiculoCreateDTO createDTO) {
        // SUGESTÃO: Normalizar a placa (maiúsculas, sem caracteres especiais)
        String placaNormalizada = normalizarPlaca(createDTO.placa());

        // VALIDAÇÃO: Verifica se a placa já existe
        if (veiculoRepository.existsByPlacaIgnoreCase(placaNormalizada)) {
            throw new IllegalArgumentException("Veículo com a placa '" + placaNormalizada + "' já está cadastrado.");
        }

        // ORQUESTRAÇÃO: Busca a entidade do proprietário pelo ID
        PessoaFisica proprietario = pessoaFisicaRepository.findById(createDTO.idProprietario())
                .orElseThrow(() -> new IllegalArgumentException("Proprietário com ID " + createDTO.idProprietario() + " não encontrado."));

        // Mapeamento de DTO para Entidade
        Veiculo novoVeiculo = new Veiculo();
        novoVeiculo.setMarca(createDTO.marca());
        novoVeiculo.setModelo(createDTO.modelo());
        novoVeiculo.setAno(createDTO.ano());
        novoVeiculo.setPlaca(placaNormalizada);
        novoVeiculo.setQuilometragem(createDTO.quilometragem());
        novoVeiculo.setProprietario(proprietario);

        Veiculo veiculoSalvo = veiculoRepository.save(novoVeiculo);

        return toResponseDTO(veiculoSalvo);
    }

    @Transactional
    public VeiculoResponseDTO update(Long id, VeiculoCreateDTO updateDTO) {
        String placaNormalizada = normalizarPlaca(updateDTO.placa());

        Veiculo veiculoExistente = veiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veículo com ID " + id + " não encontrado."));

        // VALIDAÇÃO: Se a placa mudou, verifica se a nova placa já não pertence a OUTRO veículo
        if (!veiculoExistente.getPlaca().equalsIgnoreCase(placaNormalizada)) {
            veiculoRepository.findByPlacaIgnoreCase(placaNormalizada).ifPresent(outroVeiculo -> {
                if (outroVeiculo.getIdVeiculo() != id) {
                    throw new IllegalArgumentException("A nova placa '" + placaNormalizada + "' já pertence a outro veículo.");
                }
            });
        }

        PessoaFisica proprietario = pessoaFisicaRepository.findById(updateDTO.idProprietario())
                .orElseThrow(() -> new IllegalArgumentException("Proprietário com ID " + updateDTO.idProprietario() + " não encontrado."));

        // Atualiza os dados
        veiculoExistente.setMarca(updateDTO.marca());
        veiculoExistente.setModelo(updateDTO.modelo());
        veiculoExistente.setAno(updateDTO.ano());
        veiculoExistente.setPlaca(placaNormalizada);
        veiculoExistente.setQuilometragem(updateDTO.quilometragem());
        veiculoExistente.setProprietario(proprietario);

        Veiculo veiculoAtualizado = veiculoRepository.save(veiculoExistente);
        return toResponseDTO(veiculoAtualizado);
    }

    @Transactional(readOnly = true)
    public List<VeiculoResponseDTO> findAll() {
        return veiculoRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VeiculoResponseDTO findById(Long id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veiculo com ID " + id + " não encontrado."));
        return toResponseDTO(veiculo);
    }

    // --- SUGESTÕES EXTRAS ---
    @Transactional(readOnly = true)
    public VeiculoResponseDTO findByPlaca(String placa) {
        Veiculo veiculo = veiculoRepository.findByPlacaIgnoreCase(normalizarPlaca(placa))
                .orElseThrow(() -> new IllegalArgumentException("Veículo com placa '" + placa + "' não encontrado."));
        return toResponseDTO(veiculo);
    }

    @Transactional(readOnly = true)
    public List<VeiculoResponseDTO> findAllByProprietario(Long idProprietario) {
        if (!pessoaFisicaRepository.existsById(idProprietario)) {
            throw new IllegalArgumentException("Proprietário com ID " + idProprietario + " não encontrado.");
        }
        return veiculoRepository.findByProprietarioId(idProprietario).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        if (!veiculoRepository.existsById(id)) {
            throw new IllegalArgumentException("Veículo com ID " + id + " não encontrado para exclusão.");
        }
        veiculoRepository.deleteById(id);
    }

    // --- MÉTODOS AUXILIARES ---
    private String normalizarPlaca(String placa) {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("A placa não pode ser nula ou vazia.");
        }
        return placa.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }

    private VeiculoResponseDTO toResponseDTO(Veiculo veiculo) {
        ProprietarioDTO proprietarioDTO = new ProprietarioDTO(
                veiculo.getProprietario().getId(),
                veiculo.getProprietario().getNome()
        );
        return new VeiculoResponseDTO(
                veiculo.getIdVeiculo(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getAno(),
                veiculo.getPlaca(),
                veiculo.getQuilometragem(),
                proprietarioDTO
        );
    }
}