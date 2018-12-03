package com.gilvam.cursomc.resources;

import com.gilvam.cursomc.domain.Client;
import com.gilvam.cursomc.dto.ClientDTO;
import com.gilvam.cursomc.dto.ClientNewDTO;
import com.gilvam.cursomc.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClientNewDTO clientDto) {
		Client client = this.clientService.fromDTO(clientDto);
		client = this.clientService.insert(client);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(client.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO clientDto, @PathVariable Integer id) {
		Client client = this.clientService.fromDTO(clientDto);
		client.setId(id);
		client = this.clientService.update(client);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		this.clientService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClientDTO>> findAll() {
		List<Client> categories = this.clientService.findAll();
		List<ClientDTO> clientDtoList = categories.stream().map(item -> new ClientDTO(item)).collect(Collectors.toList());
		return ResponseEntity.ok().body(clientDtoList);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
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

	@RequestMapping(value="/picture", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file) {
		URI uri = this.clientService.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}
}
