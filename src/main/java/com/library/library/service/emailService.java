package com.library.library.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class emailService {

    JavaMailSender mailSender;

    @Autowired
    public emailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Value("$(aounillibrary@gmail.com)")
    private String fromEmailAddress;

    public void sendEmail(String toEmailAddress, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmailAddress);//from the address well be sent
        message.setTo(toEmailAddress); //where is it going
        message.setSubject(subject); //objet of the email
        message.setText(body); //the email text

        mailSender.send(message);
    }

}
