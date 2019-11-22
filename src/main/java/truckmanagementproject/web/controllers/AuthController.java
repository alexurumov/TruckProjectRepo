package truckmanagementproject.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
    }
}
