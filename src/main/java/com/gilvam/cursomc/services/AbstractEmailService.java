package com.gilvam.cursomc.services;

import com.gilvam.cursomc.domain.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Order obj){
        SimpleMailMessage msg = prepareSimpleMailMessageFromOrder(obj);
        sendEmail(msg);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order obj){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(obj.getClient().getEmail());
        msg.setFrom(this.sender);
        msg.setSubject("Pedido confirmado! Código: " + obj.getId());
        msg.setSentDate(new Date(System.currentTimeMillis())); // data do meu servidor
        msg.setText(obj.toString());
        return msg;
    }
}
