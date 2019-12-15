package truckmanagementproject.web.controllers.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.services.auth.AuthService;
import truckmanagementproject.services.services.managers.ManagerService;
import truckmanagementproject.services.models.managers.AddManagerServiceModel;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.managers.AddManagerModel;
import truckmanagementproject.web.models.managers.ManagerViewModel;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/managers")
public class ManagerController {

    @ModelAttribute
    public AddManagerModel model() {
        return new AddManagerModel();
    }

    private final ModelMapper mapper;
    private final ManagerService managerService;
    private final AuthService authService;

    @Autowired
    public ManagerController(ModelMapper mapper, ManagerService managerService, AuthService authService) {
        this.mapper = mapper;
        this.managerService = managerService;
        this.authService = authService;
    }

    @GetMapping("/add")
    public String getAddManagerForm(@ModelAttribute("model") AddManagerModel model, HttpSession session) throws Exception {
        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        if (!authService.isUserAdmin(user)) {
            throw new Exception("Unauthorized user");
        }
        return "managers/add";
    }

    @PostMapping("/add")
    public String addManager(@Valid @ModelAttribute("model") AddManagerModel model, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "managers/add";
        }

        AddManagerServiceModel serviceModel = mapper.map(model, AddManagerServiceModel.class);
        try {
            managerService.registerManager(serviceModel);
            return "redirect:/managers/all";
        } catch (Exception e) {
            return "redirect:/managers/add";
        }
    }

    @GetMapping("/all")
    public ModelAndView getAllManagers(ModelAndView modelAndView, HttpSession session) throws Exception {
        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        if (!authService.isUserAdmin(user) && !authService.isUserManager(user)) {
            throw new Exception("Unauthorized user");
        }
        List<ManagerViewModel> managers = managerService.getAllManagers()
                .stream()
                .map(manager -> mapper.map(manager, ManagerViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("managers", managers);
        modelAndView.setViewName("/managers/all");
        return modelAndView;
    }

    @GetMapping("/remove/{id}")
    public ModelAndView removeManager(@PathVariable String id, HttpSession session) throws Exception {
        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        if (!authService.isUserAdmin(user)) {
            throw new Exception("Unauthorized user");
        }
        managerService.removeManager(id);
        return new ModelAndView("redirect:/managers/all");
    }
}
