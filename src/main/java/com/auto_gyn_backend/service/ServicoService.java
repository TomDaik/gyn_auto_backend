package com.auto_gyn_backend.service;

import com.auto_gyn_backend.dto.ServicoCreateDTO;
import com.auto_gyn_backend.dto.ServicoDTO;
import com.auto_gyn_backend.entity.Servico;
import com.auto_gyn_backend.repository.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }
    /**
     * Cria um novo serviço.
     */
    @Transactional
    public ServicoDTO save(ServicoCreateDTO createDTO) {
        // Regra de Negócio: O valor do serviço não pode ser negativo.
        if (createDTO.valorUnitario() < 0) {
            throw new IllegalArgumentException("O valor unitário do serviço não pode ser negativo.");
        }

        Servico servico = new Servico();
        servico.setDescricao(createDTO.descricao());
        servico.setValorUnitario(createDTO.valorUnitario());

        Servico servicoSalvo = servicoRepository.save(servico);
        return toDTO(servicoSalvo);
    }

    /**
     * Atualiza um serviço existente.
     */
    @Transactional
    public void update(Long id, ServicoCreateDTO updateDTO) {
        if (updateDTO.valorUnitario() < 0) {
            throw new IllegalArgumentException("O valor unitário do serviço não pode ser negativo.");
        }

        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço com ID " + id + " não encontrado."));

        servicoExistente.setDescricao(updateDTO.descricao());
        servicoExistente.setValorUnitario(updateDTO.valorUnitario());

        servicoRepository.save(servicoExistente);
    }

    /**
     * Busca todos os serviços.
     */
    @Transactional(readOnly = true)
    public List<ServicoDTO> findAll() {
        return servicoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um serviço pelo ID.
     */
    @Transactional(readOnly = true)
    public ServicoDTO findById(Long id) {
        return servicoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Serviço com ID " + id + " não encontrado."));
    }

    /**
     * Deleta um serviço pelo ID.
     */
    @Transactional
    public void delete(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new IllegalArgumentException("Serviço com ID " + id + " não encontrado para exclusão.");
        }
        servicoRepository.deleteById(id);
    }

    /**
     * Converte uma entidade Servico para ServicoDTO.
     */
    private ServicoDTO toDTO(Servico servico) {
        return new ServicoDTO(servico.getId(), servico.getDescricao(), servico.getValorUnitario());
    }
}