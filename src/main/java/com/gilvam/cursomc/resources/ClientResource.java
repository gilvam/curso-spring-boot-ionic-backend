package com.gilvam.cursomc.resources;

import com.gilvam.cursomc.domain.Client;
import com.gilvam.cursomc.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService clientService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Client> find(@PathVariable Integer id) {
		Client client = this.clientService.find(id);
		return ResponseEntity.ok().body(client);
	}
}
