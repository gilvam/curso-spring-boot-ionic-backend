package com.gilvam.cursomc.services;

import com.gilvam.cursomc.domain.Client;
import com.gilvam.cursomc.dto.ClientDTO;
import com.gilvam.cursomc.repositories.ClientRepository;
import com.gilvam.cursomc.services.exceptions.DataIntegrityException;
import com.gilvam.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repo;

    public Client find(Integer id) {
        Optional<Client> opt = this.repo.findById(id);
        return opt.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Client.class.getName()));
    }

//	public Client insert(Client client) {
//		client.setId(null);
//		return this.repo.save(client);
//	}

    public Client update(Client client) {
        Client clientNew = this.find(client.getId());
        updateData(clientNew, client);
        return this.repo.save(clientNew);
    }

    public void delete(Integer id) {
        find(id);
        try {
            this.repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
        }
    }

    public List<Client> findAll() {
        return this.repo.findAll();
    }

    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return this.repo.findAll(pageRequest);
    }

    public Client fromDTO(ClientDTO objDto) {
        return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
    }

    private void updateData(Client clientNew, Client client){
        clientNew.setName(client.getName());
        clientNew.setEmail(client.getEmail());
    }
}
