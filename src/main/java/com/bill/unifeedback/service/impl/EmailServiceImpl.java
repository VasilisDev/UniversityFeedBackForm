package com.bill.unifeedback.service.impl;

import com.bill.unifeedback.model.form.EmailFeedback;
import com.bill.unifeedback.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    public void sendSimpleMessage(EmailFeedback emailFeedback) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailFeedback.getReceiverEmail());
            message.setFrom(emailFeedback.getSenderEmail());
            message.setSubject(emailFeedback.getSubject());
            message.setText(emailFeedback.getBody());
            emailSender.send(message);

        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

}
