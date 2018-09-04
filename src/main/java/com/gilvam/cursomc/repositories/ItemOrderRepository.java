package com.gilvam.cursomc.repositories;

import com.gilvam.cursomc.domain.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrder, Integer> {
}
