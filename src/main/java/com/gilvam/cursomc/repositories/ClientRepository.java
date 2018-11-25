package com.gilvam.cursomc.repositories;

import com.gilvam.cursomc.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Transactional(readOnly = true) // somente leitura no banco de dados
    Client findByEmail(String email);
}
