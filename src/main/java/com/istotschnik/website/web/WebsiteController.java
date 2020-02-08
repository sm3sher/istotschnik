package com.istotschnik.website.web;

import com.istotschnik.website.model.Contact;
import com.istotschnik.website.service.MailServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import java.util.Locale;

@Controller
public class WebsiteController {

    private final MailServiceInterface mailService;
    private final String[] gerMails = {"roman.jum99@gmail.com", "owhstang@gmail.com", "stschastliviy@gmail.com"};
    private final String[] ruMails = {"roman.jum99@gmail.com", "owhstang@gmail.com", "stschastliviy@gmail.com"};

    public WebsiteController(MailServiceInterface mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String send(@ModelAttribute("form") Contact contact, Model model, Locale locale) {
        try {
            if (locale.toString().contains("de")) {
                mailService.sendToGerAddresses(gerMails, contact);
            } else {
                mailService.sendToRuAddresses(ruMails, contact);
            }
            model.addAttribute("msg", "success");
        } catch (MessagingException e) {
            model.addAttribute("msg", "error");
        }
        return "index";
    }
}


