package com.auto_gyn_backend.repository;

import com.auto_gyn_backend.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    boolean existsByPlacaIgnoreCase(String placa);

    Optional<Veiculo> findByPlacaIgnoreCase(String placa);
    
    List<Veiculo> findByProprietarioId(Long idProprietario);
}