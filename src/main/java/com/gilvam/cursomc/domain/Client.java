package com.gilvam.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gilvam.cursomc.enums.TypeClient;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String email;

	private String cpfOrCnpj;

	private Integer typeClient;

	@JsonManagedReference //gerenciado pelo json. Venha os objetos associados. | Pode serealizar o list addresses
	@OneToMany(mappedBy = "client")// state foi quem mapeou
	private List<Address> addresses= new ArrayList<>();

	@ElementCollection
	@CollectionTable(name="phone")
	private Set<String> phones = new HashSet<>(); //conjunto que nao aceita repeticao

	public Client() {
	}

	public Client(Integer id, String name, String email, String cpfOrCnpj, TypeClient typeClient) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpfOrCnpj = cpfOrCnpj;
		this.typeClient = typeClient.getCod();
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOrCnpj() {
		return cpfOrCnpj;
	}

	public void setCpfOrCnpj(String cpfOrCnpj) {
		this.cpfOrCnpj = cpfOrCnpj;
	}

	public TypeClient getTypeClient() {
		return TypeClient.toEnum(typeClient);
	}

	public void setTypeClient(TypeClient typeClient) {
		this.typeClient = typeClient.getCod();
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<String> getPhones() {
		return phones;
	}

	public void setPhones(Set<String> phones) {
		this.phones = phones;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Client)) return false;
		Client client = (Client) o;
		return Objects.equals(getId(), client.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return "Client{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", cpfOrCnpj='" + cpfOrCnpj + '\'' +
				", typeClient=" + typeClient +
				", addresses=" + addresses +
				", phones=" + phones +
				'}';
	}
}
