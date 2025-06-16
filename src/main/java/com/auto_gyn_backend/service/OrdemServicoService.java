package com.auto_gyn_backend.service;

import com.auto_gyn_backend.dto.*;
import com.auto_gyn_backend.entity.*;
import com.auto_gyn_backend.enums.StatusOrdemServico;
import com.auto_gyn_backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdemServicoService {

    // 1. ESTRUTURA E DEPENDÊNCIAS: Injetamos todos os repositórios que vamos orquestrar.
    private final OrdemServicoRepository ordemServicoRepository;
    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final VeiculoRepository veiculoRepository;
    private final PecaRepository pecaRepository;
    private final ServicoRepository servicoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ItemPecaRepository itemPecaRepository; // Repositório para os itens
    private final ItemServicoRepository itemServicoRepository; // Repositório para os itens


    public OrdemServicoService(OrdemServicoRepository ordemServicoRepository,
                               PessoaFisicaRepository pessoaFisicaRepository,
                               VeiculoRepository veiculoRepository,
                               PecaRepository pecaRepository,
                               ServicoRepository servicoRepository,
                               FuncionarioRepository funcionarioRepository,
                               ItemPecaRepository itemPecaRepository,
                               ItemServicoRepository itemServicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.veiculoRepository = veiculoRepository;
        this.pecaRepository = pecaRepository;
        this.servicoRepository = servicoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.itemPecaRepository = itemPecaRepository;
        this.itemServicoRepository = itemServicoRepository;
    }

    /**
     * 2. CRIAÇÃO DA ORDEM DE SERVIÇO (O MÉTODO PRINCIPAL)
     * Cria uma OS completa de forma transacional.
     */
    @Transactional
    public OrdemServicoResponseDTO criarOS(OrdemServicoCreateDTO dto) {
        // 2.1. Busca e valida as entidades principais
        PessoaFisica cliente = pessoaFisicaRepository.findById(dto.idCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente com ID " + dto.idCliente() + " não encontrado."));

        Veiculo veiculo = veiculoRepository.findById(dto.idVeiculo())
                .orElseThrow(() -> new IllegalArgumentException("Veículo com ID " + dto.idVeiculo() + " não encontrado."));

        // 2.2. Regra de Negócio: O veículo deve pertencer ao cliente
        if (veiculo.getProprietario().getId() != cliente.getId()) {
            throw new IllegalArgumentException("O veículo de placa " + veiculo.getPlaca() + " não pertence ao cliente " + cliente.getNome());
        }

        // 2.3. Cria a Ordem de Serviço base
        OrdemServico os = new OrdemServico(cliente, veiculo, LocalDate.now());
        os.setObservacoes(dto.observacoes());

        // 2.4. Processa e adiciona as peças, dando baixa no estoque
        if (dto.pecas() != null) {
            for (ItemPecaDTO itemDto : dto.pecas()) {
                adicionarPecaNaOS(os, itemDto);
            }
        }

        // 2.5. Processa e adiciona os serviços
        if (dto.servicos() != null) {
            for (ItemServicoDTO itemDto : dto.servicos()) {
                adicionarServicoNaOS(os, itemDto);
            }
        }

        // 2.6. Calcula o valor total e salva a OS com todos os seus itens
        recalcularValorTotal(os);
        OrdemServico osSalva = ordemServicoRepository.save(os);

        // 2.7. Converte a entidade salva para um DTO de resposta detalhado
        return toResponseDTO(osSalva);
    }

    /**
     * 3. GERENCIAMENTO DE ITENS (ADICIONAR/REMOVER)
     */
    @Transactional
    public OrdemServicoResponseDTO adicionarPecaEmOS(Long osId, ItemPecaDTO itemDto) {
        OrdemServico os = findByIdInternal(osId);
        validarSeOSEstaAberta(os);
        adicionarPecaNaOS(os, itemDto);
        recalcularValorTotal(os);
        return toResponseDTO(ordemServicoRepository.save(os));
    }

    @Transactional
    public void removerPecaDeOS(Long osId, Long itemPecaId) {
        OrdemServico os = findByIdInternal(osId);
        validarSeOSEstaAberta(os);

        ItemPeca itemParaRemover = itemPecaRepository.findById(itemPecaId)
                .orElseThrow(() -> new IllegalArgumentException("Item de Peça com ID " + itemPecaId + " não encontrado."));

        if(itemParaRemover.getOrdemServico().getId() != osId) {
            throw new IllegalStateException("O item de peça não pertence a esta Ordem de Serviço.");
        }

        // Estorna a peça para o estoque
        Peca peca = itemParaRemover.getPeca();
        peca.setQuantidade(peca.getQuantidade() + itemParaRemover.getQuantidade());

        // Remove o item da lista e do banco de dados
        os.getItensPeca().remove(itemParaRemover);
        itemPecaRepository.delete(itemParaRemover);

        recalcularValorTotal(os);
        ordemServicoRepository.save(os);
    }

    // Implementação dos métodos para adicionar/remover serviços
    @Transactional
    public OrdemServicoResponseDTO adicionarServicoEmOS(Long osId, ItemServicoDTO itemDto) {
        OrdemServico os = findByIdInternal(osId);
        validarSeOSEstaAberta(os);
        adicionarServicoNaOS(os, itemDto);
        recalcularValorTotal(os);
        return toResponseDTO(ordemServicoRepository.save(os));
    }

    @Transactional
    public void removerServicoDeOS(Long osId, Long itemServicoId) {
        OrdemServico os = findByIdInternal(osId);
        validarSeOSEstaAberta(os);

        ItemServico itemParaRemover = itemServicoRepository.findById(itemServicoId)
                .orElseThrow(() -> new IllegalArgumentException("Item de Serviço com ID " + itemServicoId + " não encontrado."));

        if (itemParaRemover.getOrdemServico().getId() != osId) {
            throw new IllegalStateException("O item de serviço não pertence a esta Ordem de Serviço.");
        }

        os.getItensServico().remove(itemParaRemover);
        itemServicoRepository.delete(itemParaRemover);

        recalcularValorTotal(os);
        ordemServicoRepository.save(os);
    }


    /**
     * 4. GERENCIAMENTO DE STATUS (FINALIZAR/CANCELAR)
     */
    @Transactional
    public void finalizarOS(Long id) {
        OrdemServico os = findByIdInternal(id);
        validarSeOSEstaAberta(os);
        os.setStatus(StatusOrdemServico.PAGO);
        os.setDataFechamento(LocalDate.now());
        ordemServicoRepository.save(os);
    }

    @Transactional
    public void cancelarOS(Long id) {
        OrdemServico os = findByIdInternal(id);
        validarSeOSEstaAberta(os);
        os.setStatus(StatusOrdemServico.CANCELADO);
        os.setDataFechamento(LocalDate.now());

        // Regra de Negócio: Estorna as peças para o estoque
        for(ItemPeca item : os.getItensPeca()) {
            Peca peca = item.getPeca();
            peca.setQuantidade(peca.getQuantidade() + item.getQuantidade());
        }
        ordemServicoRepository.save(os);
    }

    /**
     * 5. MÉTODOS DE CONSULTA E EXCLUSÃO
     */
    @Transactional(readOnly = true)
    public OrdemServicoResponseDTO findDetailedById(Long id) {
        return ordemServicoRepository.findById(id).map(this::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de Serviço com ID " + id + " não encontrada."));
    }

    @Transactional(readOnly = true)
    public List<OrdemServicoResponseDTO> findAllDetailed() {
        return ordemServicoRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        OrdemServico os = findByIdInternal(id);
        if (os.getStatus() != StatusOrdemServico.EM_ABERTO) {
            throw new IllegalStateException("Não é possível excluir uma OS que já foi finalizada ou cancelada.");
        }
        ordemServicoRepository.deleteById(id);
    }

    /**
     * 6. MÉTODOS AUXILIARES (HELPERS)
     */
    private OrdemServico findByIdInternal(Long osId) {
        return ordemServicoRepository.findById(osId)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de Serviço com ID " + osId + " não encontrada."));
    }

    private void validarSeOSEstaAberta(OrdemServico os) {
        if (os.getStatus() != StatusOrdemServico.EM_ABERTO) {
            throw new IllegalStateException("A operação só é permitida em Ordens de Serviço com status 'EM ABERTO'.");
        }
    }

    private void adicionarPecaNaOS(OrdemServico os, ItemPecaDTO itemDto) {
        Peca peca = pecaRepository.findById(itemDto.idPeca())
                .orElseThrow(() -> new IllegalArgumentException("Peça com ID " + itemDto.idPeca() + " não encontrada."));
        if (peca.getQuantidade() < itemDto.quantidade()) {
            throw new IllegalStateException("Estoque insuficiente para a peça: " + peca.getDescricao());
        }
        peca.setQuantidade(peca.getQuantidade() - itemDto.quantidade());
        os.adicionarPeca(peca, itemDto.quantidade());
    }

    private void adicionarServicoNaOS(OrdemServico os, ItemServicoDTO itemDto) {
        Servico servico = servicoRepository.findById(itemDto.idServico())
                .orElseThrow(() -> new IllegalArgumentException("Serviço com ID " + itemDto.idServico() + " não encontrado."));
        Funcionario funcionario = funcionarioRepository.findById(itemDto.idFuncionario())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário com ID " + itemDto.idFuncionario() + " não encontrado."));
        os.adicionarServico(servico, funcionario);
    }

    private void recalcularValorTotal(OrdemServico os) {
        double totalPecas = os.getItensPeca().stream().mapToDouble(item -> item.getValorUnitarioCobrado() * item.getQuantidade()).sum();
        double totalServicos = os.getItensServico().stream().mapToDouble(ItemServico::getValorCobrado).sum();
        os.setValorTotal(totalPecas + totalServicos);
    }

    private OrdemServicoResponseDTO toResponseDTO(OrdemServico os) {
        ProprietarioDTO clienteDto = new ProprietarioDTO(os.getCliente().getId(), os.getCliente().getNome());

        VeiculoResponseDTO veiculoDto = new VeiculoResponseDTO(
                os.getVeiculo().getIdVeiculo(), os.getVeiculo().getMarca(), os.getVeiculo().getModelo(),
                os.getVeiculo().getAno(), os.getVeiculo().getPlaca(), os.getVeiculo().getQuilometragem(),
                clienteDto
        );

        List<ItemServicoResponseDTO> itensServicoDto = os.getItensServico().stream()
                .map(item -> new ItemServicoResponseDTO(item.getServico().getDescricao(), item.getValorCobrado(), item.getFuncionario().getNome()))
                .collect(Collectors.toList());

        List<ItemPecaResponseDTO> itensPecaDto = os.getItensPeca().stream()
                .map(item -> new ItemPecaResponseDTO(item.getPeca().getDescricao(), item.getQuantidade(), item.getValorUnitarioCobrado(), item.getQuantidade() * item.getValorUnitarioCobrado()))
                .collect(Collectors.toList());

        return new OrdemServicoResponseDTO(
                os.getId(), os.getDataAbertura(), os.getDataFechamento(), os.getStatus(),
                os.getObservacoes(), os.getValorTotal(), clienteDto, veiculoDto,
                itensServicoDto, itensPecaDto
        );
    }
}