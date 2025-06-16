package com.auto_gyn_backend.repository;

import com.auto_gyn_backend.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    /** Busca todos os funcionários com um determinado cargo, ignorando maiúsculas/minúsculas. (NOVO) */
    List<Funcionario> findByCargoIgnoreCase(String cargo);
}