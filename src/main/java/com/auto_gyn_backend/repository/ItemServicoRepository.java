package com.auto_gyn_backend.repository;

import com.auto_gyn_backend.entity.ItemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemServicoRepository extends JpaRepository<ItemServico, Long> {
}
