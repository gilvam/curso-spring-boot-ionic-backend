package com.gilvam.cursomc.services;

import java.util.List;

import com.gilvam.cursomc.domain.City;
import com.gilvam.cursomc.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityRepository repo;

    public List<City> findByState(Integer stateId) {
        return repo.findCities(stateId);
    }
}


