package com.istotschnik.website.web;

import com.istotschnik.website.model.Contact;
import com.istotschnik.website.service.MailServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "/.well-known/acme-challenge/U3tgeSTDuX2Mb50MS4Bj1jfeCVmTYzGQ9WL6tLzRoso",
            method = RequestMethod.GET)
    @ResponseBody
    public String certBot(HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return "U3tgeSTDuX2Mb50MS4Bj1jfeCVmTYzGQ9WL6tLzRoso.pBtT2AhjrL2ZSpy2bbkFctc3iHoOifqPWn7TZQ-FDI0";
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


