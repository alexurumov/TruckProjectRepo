package truckmanagementproject.web.controllers.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomepage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String getHome(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "home";
        }
        return "index";
    }
}
