package com.istotschnik.website.service;

import com.istotschnik.website.model.Contact;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service("mailservice")
public class MailServiceImpl implements MailServiceInterface {

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private String extractContentGer(Contact contact) {
        String content = "Name: " + contact.getName();
        content += "\nE-Mail: " + contact.getEmail();
        content += "\n\n" + contact.getMessage();
        return content;
    }

    private String extractContentRu(Contact contact) {
        String content = "Имя: " + contact.getName();
        content += "\nЭлектронная почта: " + contact.getEmail();
        content += "\n\n" + contact.getMessage();
        return content;
    }

    private String confirmContentGer(Contact contact) {
        String content = "Vielen Dank für Ihre Kontaktaufnahme " + contact.getName() + ",";
        content += "\n\nIhre Nachricht ist bei uns eingegangen und wird so schnell wie möglich beantwortet.";
        content += "\nIm folgenden sehen Sie eine Kopie Ihrer Nachricht:";
        content += "\n\n" + contact.getMessage();
        content += "\n\nMit freundlichen Grüßen,\nIstotschnik e.V.";
        return content;
    }

    private String confirmContentRu(Contact contact) {
        String content = "Спасибо, что связались с нами " + contact.getName() + ",";
        content += "\n\nМы получили ваше сообщение и ответим на него как можно скорее.";
        content += "\nНиже приведена копия вашего сообщения:";
        content += "\n\n" + contact.getMessage();
        content += "\n\nС наилучшими пожеланиями,\nИсточник e.V.";
        return content;
    }

    private void send(String toAddress, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("Istotschnik e.V. <istotschnik.mailservice@gmail.com>");
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message);
        mimeMessageHelper.setSentDate(new Date());
        javaMailSender.send(mimeMessage);
    }

    public void sendToGerAddresses(String[] gerMails, Contact contact) throws MessagingException {
        for (String mail: gerMails) {
            send(mail, "Istotschnik Website - " + contact.getSubject(), extractContentGer(contact));
        }
        send(contact.getEmail(), "Istotschnik - Bestätigungsmail: " + contact.getSubject(), confirmContentGer(contact));
    }

    public void sendToRuAddresses(String[] ruMails, Contact contact) throws MessagingException {
        for (String mail: ruMails) {
            send(mail, "Сайт Источник - " + contact.getSubject(), extractContentRu(contact));
        }
        send(contact.getEmail(), "Источник - Подтверждение: " + contact.getSubject(), confirmContentRu(contact));
    }

}
