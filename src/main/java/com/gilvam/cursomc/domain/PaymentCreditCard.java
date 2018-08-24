package com.gilvam.cursomc.domain;

import com.gilvam.cursomc.enums.PaymentStatus;

import javax.persistence.Entity;

@Entity
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
