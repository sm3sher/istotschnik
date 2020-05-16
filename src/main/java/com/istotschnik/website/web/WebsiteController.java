package com.istotschnik.website.web;

import com.istotschnik.website.model.Contact;
import com.istotschnik.website.model.ReCaptchaResponse;
import com.istotschnik.website.service.MailServiceInterface;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.util.Locale;

@Controller
public class WebsiteController {

    private final MailServiceInterface mailService;
    private final RestTemplate restTemplate;
//    private final String[] gerMails = {"roman.jum99@gmail.com", "owhstang@gmail.com", "stschastliviy@gmail.com"};
    private final String[] gerMails = {"anime.provider2016@gmail.com"};
//    private final String[] ruMails = {"roman.jum99@gmail.com", "owhstang@gmail.com", "stschastliviy@gmail.com"};
    private final String[] ruMails = {"roman.jum99@gmail.com"};

    public WebsiteController(MailServiceInterface mailService, RestTemplate restTemplate) {
        this.mailService = mailService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String send(@ModelAttribute("form") Contact contact, Model model, Locale locale,
                       @RequestParam(name="g-recaptcha-response") String captchaResponse) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "?secret=6LezNPgUAAAAAIKmmggLTz-wjVkzBMxtKZVEjgqB&response="+captchaResponse;
        ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url+params, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();
        if (reCaptchaResponse.isSuccess()) {
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
        }
        else {
            model.addAttribute("msg", "captcha");
        }
        return "index";
    }
}


