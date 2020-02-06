package com.istotschnik.website.service;

import com.istotschnik.website.model.Contact;

import javax.mail.MessagingException;

public interface MailServiceInterface {

    void sendToGerAddresses(String[] gerMails, Contact contact) throws MessagingException;
    void sendToRuAddresses(String[] ruMails, Contact contact) throws MessagingException;

}
