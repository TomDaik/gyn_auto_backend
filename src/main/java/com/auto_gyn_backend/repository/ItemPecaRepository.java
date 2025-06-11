package com.auto_gyn_backend.repository;

import com.auto_gyn_backend.entity.ItemPeca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPecaRepository extends JpaRepository<ItemPeca, Long> {

}
