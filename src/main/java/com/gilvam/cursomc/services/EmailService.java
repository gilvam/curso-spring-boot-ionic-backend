package com.gilvam.cursomc.services;

import com.gilvam.cursomc.domain.Order;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Order obj);
    void sendEmail(SimpleMailMessage msg);
}
