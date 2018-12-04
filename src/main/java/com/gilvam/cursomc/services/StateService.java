package com.gilvam.cursomc.services;

import java.util.List;

import com.gilvam.cursomc.domain.State;
import com.gilvam.cursomc.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    @Autowired
    private StateRepository repo;

    public List<State> findAll() {
        return repo.findAllByOrderByName();
    }
}