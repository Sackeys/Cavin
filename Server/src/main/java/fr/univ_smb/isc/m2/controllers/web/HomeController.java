package fr.univ_smb.isc.m2.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    @RequestMapping("/")
    public View home() {
        return new RedirectView("/view/index.html");
    }

    @RequestMapping("/bottle")
    public View bottle() {
        return new RedirectView("/view/bottle.html");
    }

    @RequestMapping("/cellar")
    public View cellar() {
        return new RedirectView("/view/cellar.html");
    }
}
