package truckmanagementproject.web.controllers.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import truckmanagementproject.services.services.auth.AuthService;
import truckmanagementproject.services.models.auth.LoginUserServiceModel;
import truckmanagementproject.web.models.auth.LoginUserModel;
import truckmanagementproject.web.models.auth.LoginUserViewModel;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final AuthService authService;
    private final ModelMapper mapper;

    @Autowired
    public AuthController(AuthService authService, ModelMapper mapper) {
        this.authService = authService;
        this.mapper = mapper;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginUserModel model, HttpSession httpSession) {
        LoginUserServiceModel user = mapper.map(model, LoginUserServiceModel.class);
        LoginUserServiceModel userModel = authService.login(user);

        if (userModel == null) {
            return "redirect:/login";
        }

        LoginUserViewModel userViewModel = mapper.map(userModel, LoginUserViewModel.class);
        userViewModel.setShortUsername(userViewModel.getUsername().substring(0, 2));
        httpSession.setAttribute("user", userViewModel);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

}
