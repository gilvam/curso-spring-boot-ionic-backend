package com.gilvam.cursomc.resources;

import com.gilvam.cursomc.domain.Category;
import com.gilvam.cursomc.domain.Client;
import com.gilvam.cursomc.dto.CategoryDTO;
import com.gilvam.cursomc.dto.ClientDTO;
import com.gilvam.cursomc.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO clientDto, @PathVariable Integer id) {
		Client client = this.clientService.fromDTO(clientDto);
		client.setId(id);
		client = this.clientService.update(client);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		this.clientService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClientDTO>> findAll() {
		List<Client> categories = this.clientService.findAll();
		List<ClientDTO> clientDtoList = categories.stream().map(item -> new ClientDTO(item)).collect(Collectors.toList());
		return ResponseEntity.ok().body(clientDtoList);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClientDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction)
	{
		Page<Client> clientPage = this.clientService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClientDTO> clientDtoPage = clientPage.map(item -> new ClientDTO(item));
		return ResponseEntity.ok().body(clientDtoPage);
	}
}
