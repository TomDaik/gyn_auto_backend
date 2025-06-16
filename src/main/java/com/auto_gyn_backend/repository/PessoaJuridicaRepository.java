package com.auto_gyn_backend.repository;

import com.auto_gyn_backend.entity.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    /** Verifica se já existe um CNPJ cadastrado. */
    boolean existsByCnpj(String cnpj);

    /** Busca uma Pessoa Jurídica pelo CNPJ. (NOVO) */
    Optional<PessoaJuridica> findByCnpj(String cnpj);
}