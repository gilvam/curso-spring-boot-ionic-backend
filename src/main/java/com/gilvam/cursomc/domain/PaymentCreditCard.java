package com.gilvam.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gilvam.cursomc.enums.PaymentStatus;

import javax.persistence.Entity;

/** classe registrada no Jackson em config/JacksonConfig.java **/

@Entity
@JsonTypeName("paymentCreditCard") //valor do campo adicional @type na classe abstrata Payment.java para selecionar essa classe
public class PaymentCreditCard extends Payment {
	private static final long serialVersionUID = 1L;

	private Integer numberInstallments; // número de parcelas

	public PaymentCreditCard() {
	}

	public PaymentCreditCard(Integer id, PaymentStatus status, Order order, Integer numberInstallments) {
		super(id, status, order);
		this.numberInstallments = numberInstallments;
	}

	public Integer getNumberInstallments() {
		return numberInstallments;
	}

	public void setNumberInstallments(Integer numberInstallments) {
		this.numberInstallments = numberInstallments;
	}


}
