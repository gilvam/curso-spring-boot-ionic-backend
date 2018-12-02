package com.gilvam.cursomc.repositories;

import com.gilvam.cursomc.domain.Client;
import com.gilvam.cursomc.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Transactional(readOnly=true)
    Page<Order> findByClient(Client client, Pageable pageRequest);
}
