package com.gilvam.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gilvam.cursomc.enums.PaymentStatus;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class PaymentBankSlip extends Payment {
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dueDate; //dia de vencimento

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date payDay; //dia de pagamento

	public PaymentBankSlip() {
	}

	public PaymentBankSlip(Integer id, PaymentStatus status, Order order, Date dueDate, Date payDay) {
		super(id, status, order);
		this.payDay = payDay;
		this.dueDate = dueDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPayDay() {
		return payDay;
	}

	public void setPayDay(Date payDay) {
		this.payDay = payDay;
	}

}