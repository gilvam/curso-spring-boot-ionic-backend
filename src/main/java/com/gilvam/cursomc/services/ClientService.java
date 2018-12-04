package com.gilvam.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

import com.gilvam.cursomc.enums.Profile;
import com.gilvam.cursomc.enums.TypeClient;
import com.gilvam.cursomc.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gilvam.cursomc.domain.City;
import com.gilvam.cursomc.domain.Client;
import com.gilvam.cursomc.domain.Address;
import com.gilvam.cursomc.dto.ClientDTO;
import com.gilvam.cursomc.dto.ClientNewDTO;
import com.gilvam.cursomc.repositories.CityRepository;
import com.gilvam.cursomc.repositories.ClientRepository;
import com.gilvam.cursomc.repositories.AddressRepository;
import com.gilvam.cursomc.services.exceptions.AuthorizationException;
import com.gilvam.cursomc.services.exceptions.DataIntegrityException;
import com.gilvam.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private ClientRepository repo;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;

    public Client find(Integer id) {

        UserSpringSecurity user = UserService.authenticated();
        if (user == null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Client obj = repo.findById(id).orElseThrow();
        if (obj == null) {
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName());
        }
        return obj;
    }

    public Client insert(Client obj) {
        obj.setId(null);
        obj = repo.save(obj);
        addressRepository.saveAll(obj.getAddresses());
        return obj;
    }

    public Client update(Client obj) {
        Client newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
        }
    }

    public List<Client> findAll() {
        return repo.findAll();
    }

    public Client findByEmail(String email) {

        UserSpringSecurity user = UserService.authenticated();
        if (user == null || !user.hasRole(Profile.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }

        Client obj = repo.findByEmail(email);
        if (obj == null) {
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Client.class.getName());
        }
        return obj;
    }

    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Client fromDTO(ClientDTO objDto) {
        return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);
    }

    public Client fromDTO(ClientNewDTO objDto) {
        Client cli = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOrCnpj(),
                TypeClient.toEnum(objDto.getType()), pe.encode(objDto.getPassword()));
        City cid = cityRepository.findById(objDto.getCityId()).orElseThrow();
        Address end = new Address(null, objDto.getAddressName(), objDto.getAddressNumber(), objDto.getAddressComplement(),
                objDto.getAddressDistrict(), objDto.getAddressZipCode(), cli, cid);
        cli.getAddresses().add(end);
        cli.getPhones().add(objDto.getPhone1());
        if (objDto.getPhone2() != null) {
            cli.getPhones().add(objDto.getPhone2());
        }
        if (objDto.getPhone3() != null) {
            cli.getPhones().add(objDto.getPhone3());
        }
        return cli;
    }

    private void updateData(Client newObj, Client obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {

        UserSpringSecurity user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);

        String fileName = prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }
}