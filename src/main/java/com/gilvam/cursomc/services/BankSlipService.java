package com.gilvam.cursomc.services;

import com.gilvam.cursomc.domain.PaymentBankSlip;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BankSlipService {

    // acrescentando 7 dias no vencimento do boleto (apenas para simular)
    public void completePaymentBankSlip(PaymentBankSlip paymentBankSlip, Date orderTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(orderTime);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        paymentBankSlip.setDueDate(cal.getTime());
    }
}
