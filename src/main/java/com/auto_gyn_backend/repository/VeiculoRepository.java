package com.auto_gyn_backend.repository;

import com.auto_gyn_backend.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {
    public void deleteVeiculoByIdVeiculo(Integer idVeiculo);
}
