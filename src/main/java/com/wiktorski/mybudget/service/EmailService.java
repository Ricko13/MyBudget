package com.wiktorski.mybudget.service;

import com.wiktorski.mybudget.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    @Async
    public void sendEmail(SimpleMailMessage email) {
        mailSender.send(email);
    }

    public SimpleMailMessage createConfirmationEmail(@Valid User user, HttpServletRequest request) {
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":8080";
        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(user.getEmail());
        registrationEmail.setSubject("MyPasswordManager - activation link");
        registrationEmail.setText("To activate your account, please click the link below:\n"
                + appUrl + "/confirm?token=" + user.getConfirmationToken());
        registrationEmail.setFrom("noreply@MyPasswordWallet.com");
        return registrationEmail;
    }

    public SimpleMailMessage createLogingInfoEmail(@Valid User user, HttpServletRequest request) {
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":8080";
        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(user.getEmail());
        registrationEmail.setSubject("MyPasswordManager - signed in");
        registrationEmail.setText("You just logged in on MyPasswordManager\nYou will receive this email always when logging in");
        registrationEmail.setFrom("noreply@MyPasswordWallet.com");
        return registrationEmail;
    }

    public SimpleMailMessage createFailedLoginEmail(@Valid User user, HttpServletRequest request) {
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":8080";
        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(user.getEmail());
        registrationEmail.setSubject("MyPasswordManager - logging in");
        registrationEmail.setText("Failed logging attempt. ");
        registrationEmail.setFrom("noreply@MyPasswordWallet.com");
        return registrationEmail;
    }
}
