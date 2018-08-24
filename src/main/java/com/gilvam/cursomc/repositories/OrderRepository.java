package com.gilvam.cursomc.repositories;

import org.springframework.stereotype.Repository;
import com.gilvam.cursomc.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
