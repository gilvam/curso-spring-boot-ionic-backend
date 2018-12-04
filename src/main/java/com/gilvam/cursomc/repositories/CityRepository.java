package com.gilvam.cursomc.repositories;

import com.gilvam.cursomc.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Transactional(readOnly=true)
    @Query("SELECT C FROM City C WHERE C.state.id = :stateId ORDER BY C.name")
    public List<City> findCities(@Param("stateId") Integer stateId);
}
