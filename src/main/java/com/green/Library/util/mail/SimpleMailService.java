package com.green.Library.util.mail;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface SimpleMailService {
    public void SimpleMailSend(String email, String text , String subject) throws MessagingException, UnsupportedEncodingException;
}
