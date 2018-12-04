package com.gilvam.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.gilvam.cursomc.domain.City;
import com.gilvam.cursomc.domain.State;
import com.gilvam.cursomc.dto.CityDTO;
import com.gilvam.cursomc.dto.StateDTO;
import com.gilvam.cursomc.services.CityService;
import com.gilvam.cursomc.services.StateService;

@RestController
@RequestMapping(value="/states")
public class StateResource {

    @Autowired
    private StateService stateService;

    @Autowired
    private CityService cityService;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<StateDTO>> findAll() {
        List<State> list = stateService.findAll();
        List<StateDTO> listDto = list.stream().map(obj -> new StateDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value="/{stateId}/cities", method=RequestMethod.GET)
    public ResponseEntity<List<CityDTO>> findCities(@PathVariable Integer stateId) {
        List<City> list = cityService.findByState(stateId);
        List<CityDTO> listDto = list.stream().map(obj -> new CityDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
}