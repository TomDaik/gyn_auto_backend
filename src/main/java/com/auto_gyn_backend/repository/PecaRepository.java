package com.auto_gyn_backend.repository;

import com.auto_gyn_backend.entity.Peca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Long> {
    // Verifica se já existe uma peça com o código (ignorando maiúsculas/minúsculas)
    boolean existsByCodigoIgnoreCase(String codigo);

    // Busca uma peça pelo código (ignorando maiúsculas/minúsculas)
    Optional<Peca> findByCodigoIgnoreCase(String codigo);
}