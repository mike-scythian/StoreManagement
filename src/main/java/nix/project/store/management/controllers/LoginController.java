package nix.project.store.management.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage(Model model) throws URISyntaxException {
        model.addAttribute("title", "Login");
        URI uri = new URI("http://localhost:8181/");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return "login";
    }
}
