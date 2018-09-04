package com.gilvam.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gilvam.cursomc.enums.PaymentStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)//usar estratégia de ter as 2 classes em 1 tabela (PaymentBankSlip e PaymentCreditCard)
public abstract class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue() -> dont use because use @MapsId in order
	private Integer id;
	private Integer status;

	@JsonBackReference //objetos são retornados 1 vez e na associação @JsonManagedReference sem realizar loop. Não pode serealizar o objeto order
	@OneToOne
	@JoinColumn(name = "order_id")
	@MapsId
	private Order order;

	public Payment() {
	}

	public Payment(Integer id, PaymentStatus status, Order order) {
		super();
		this.id = id;
		this.status = status.getCod();
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PaymentStatus getStatus() {
		return PaymentStatus.toEnum(status);
	}

	public void setStatus(PaymentStatus status) {
		this.status = status.getCod();
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}