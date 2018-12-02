package com.gilvam.cursomc.services;

import com.gilvam.cursomc.domain.Client;
import com.gilvam.cursomc.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail(Order order){
        SimpleMailMessage msg = prepareSimpleMailMessageFromOrder(order);
        sendEmail(msg);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order order){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(order.getClient().getEmail());
        msg.setFrom(this.sender);
        msg.setSubject("Pedido confirmado! Código: " + order.getId());
        msg.setSentDate(new Date(System.currentTimeMillis())); // data do meu servidor
        msg.setText(order.toString());
        return msg;
    }

    protected String htmlFromTemplatePedido(Order order){
        Context context = new Context();
        context.setVariable("order", order);
        return this.templateEngine.process("email/confirmationOrder.html", context);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Order order){
        MimeMessage mm = null;
        //caso não consiga enviar o email com html, envia o email simples
        try {
            mm = prepareMimeMessageFromOrder(order);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(order);
        }
    }

    protected MimeMessage prepareMimeMessageFromOrder(Order order) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(order.getClient().getEmail());
        mmh.setFrom(this.sender);
        mmh.setSubject("Pedido confirmado! Código: " + order.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplatePedido(order), true);
        return mimeMessage;
    }

    @Override
    public void sendNewPasswordEmail(Client client, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(client, newPass);
        sendEmail(sm);
    }
    protected SimpleMailMessage prepareNewPasswordEmail(Client client, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(client.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + newPass);
        return sm;
    }
}
