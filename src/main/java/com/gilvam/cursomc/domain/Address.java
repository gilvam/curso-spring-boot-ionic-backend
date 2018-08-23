package com.gilvam.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Integer number;
	private String complement;
	private String district;
	private String zipCode;

	@JsonBackReference //os objetos são retornados apenas 1 vez e na associação @JsonManagedReference e sem realizar loop. | Não pode serealizar o objeto client
	@ManyToOne
	@JoinColumn(name="client_id")//mapeando usando id do estado
	private Client client;

	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;

	public Address() {
	}

	public Address(Integer id, String name, Integer number, String complement, String district, String zipCode, Client client, City city) {
		this.id = id;
		this.name = name;
		this.number = number;
		this.complement = complement;
		this.district = district;
		this.zipCode = zipCode;
		this.client = client;
		this.city = city;
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

	public Number getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Address)) return false;
		Address address = (Address) o;
		return Objects.equals(getId(), address.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return "Address{" +
				"id=" + id +
				", name='" + name + '\'' +
				", number=" + number +
				", complement='" + complement + '\'' +
				", district='" + district + '\'' +
				", zipCode='" + zipCode + '\'' +
				", client=" + client +
				", city=" + city +
				'}';
	}
}
