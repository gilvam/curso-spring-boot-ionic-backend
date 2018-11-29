package com.gilvam.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "order_tb") // nome da table diferente pois "order" não pode ser utilizado
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date instante;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
	private Payment payment;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@ManyToOne
	@JoinColumn(name = "address_delivery_id")
	private Address addressDelivery;

	@OneToMany(mappedBy = "id.order")
	private Set<ItemOrder> itens = new HashSet<>(); //set ajuda a não repetir um item dentro da hashSet

	public Order() {
	}

	public Order(Integer id, Date instante, Client client, Address addressDelivery) {
		super();
		this.id = id;
		this.instante = instante;
		this.client = client;
		this.addressDelivery = addressDelivery;
	}

	public double getTotalAmount(){
		return this.itens.stream().mapToDouble(itemOrder -> itemOrder.getSubTotal()).sum();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Address getAddressDelivery() {
		return addressDelivery;
	}

	public void setAddressDelivery(Address addressDelivery) {
		this.addressDelivery = addressDelivery;
	}

	public Set<ItemOrder> getItens() {
		return itens;
	}

	public void setItens(Set<ItemOrder> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		final StringBuffer
//				sb = new StringBuffer("Order{");
				sb = new StringBuffer();
		sb.append("Pedido número: ");
		sb.append(this.getId());
		sb.append(" ,Instante: ");
		sb.append(sdf.format(this.getInstante()));
		sb.append(" ,Cliente: ");
		sb.append(this.getClient().getName());
		sb.append(" , Situação do pagamento: ");
		sb.append(this.getPayment().getStatus().getDescription());

		sb.append("\n Detalhes \n");
		for (ItemOrder io : getItens()){
			sb.append(io.toString());
		}

		sb.append("Valor total: ");
		sb.append(nf.format(this.getTotalAmount()));


//		sb.append("id=").append(id);
//		sb.append('}');
		return sb.toString();
	}
}
