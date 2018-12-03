package com.gilvam.cursomc.services;

import com.gilvam.cursomc.domain.Address;
import com.gilvam.cursomc.domain.City;
import com.gilvam.cursomc.domain.Client;
import com.gilvam.cursomc.dto.ClientDTO;
import com.gilvam.cursomc.dto.ClientNewDTO;
import com.gilvam.cursomc.enums.Profile;
import com.gilvam.cursomc.enums.TypeClient;
import com.gilvam.cursomc.repositories.AddressRepository;
import com.gilvam.cursomc.repositories.ClientRepository;
import com.gilvam.cursomc.security.UserSpringSecurity;
import com.gilvam.cursomc.services.exceptions.AuthorizationException;
import com.gilvam.cursomc.services.exceptions.DataIntegrityException;
import com.gilvam.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;


@Service
public class ClientService {

    @Autowired
    private ClientRepository repo;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private S3Service s3Service;

    public Client find(Integer id) {

        UserSpringSecurity user = UserService.authenticated();
        if (user==null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Client obj = repo.findById(id).orElse(null);

        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
                    + ", Tipo: " + Client.class.getName());
        }
        return obj;
    }

    @Transactional // garante que vai salvar tanto os clientes quanto os endereços na mesma transação no banco de dados
    public Client insert(Client client) {
        client.setId(null);
        client = this.repo.save(client);
        this.addressRepository.saveAll(client.getAddresses());
        return client;
    }

    public Client update(Client client) {
        Client clientNew = this.find(client.getId());
        this.updateData(clientNew, client);
        return this.repo.save(clientNew);
    }

    public void delete(Integer id) {
        find(id);
        try {
            this.repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
        }
    }

    public List<Client> findAll() {
        return this.repo.findAll();
    }

    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return this.repo.findAll(pageRequest);
    }

    public Client fromDTO(ClientDTO dto) {
        return new Client(dto.getId(), dto.getName(), dto.getEmail(), null, null, null);
    }

    public Client fromDTO(ClientNewDTO dto) {
        Client client = new Client(null, dto.getName(), dto.getEmail(), dto.getCpfOrCnpj(), TypeClient.toEnum(dto.getType()), pe.encode(dto.getPassword()));
        City city = new City(dto.getCityId(), null, null);

        Address address = new Address(null, dto.getAddressName(), dto.getAddressNumber(), dto.getAddressComplement(), dto.getAddressDistrict(), dto.getAddressZipCode(), client, city);

        client.getAddresses().add(address);
        client.getPhones().add(dto.getPhone1());

        if (dto.getPhone2() != null) {
            client.getPhones().add(dto.getPhone2());
        }
        if (dto.getPhone3() != null) {
            client.getPhones().add(dto.getPhone3());
        }
        return client;
    }

    private void updateData(Client clientNew, Client client) {
        clientNew.setName(client.getName());
        clientNew.setEmail(client.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        UserSpringSecurity user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        URI uri = this.s3Service.uploadFile(multipartFile);

        Client client = this.repo.findById(user.getId()).orElseThrow();
        client.setImageUrl(uri.toString());
        repo.save(client);

        return uri;
    }
}
