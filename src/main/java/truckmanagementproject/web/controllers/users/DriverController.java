package truckmanagementproject.web.controllers.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.services.auth.AuthService;
import truckmanagementproject.services.services.drivers.DriverService;
import truckmanagementproject.services.models.drivers.AddDriverServiceModel;
import truckmanagementproject.web.controllers.base.BaseController;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.drivers.AddDriverModel;
import truckmanagementproject.web.models.drivers.DriverViewModel;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/drivers")
public class DriverController extends BaseController {

    @ModelAttribute
    public AddDriverModel model() {
        return new AddDriverModel();
    }

    private final ModelMapper mapper;
    private final DriverService driverService;

    @Autowired
    public DriverController(ModelMapper mapper, DriverService driverService, AuthService authService) {
        super(authService);
        this.mapper = mapper;
        this.driverService = driverService;
    }

    @GetMapping("/add")
    public String getAddDriverForm(@ModelAttribute("model") AddDriverModel model, HttpSession session) throws Exception {
        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdmin(user);

        return "drivers/add";
    }

    @PostMapping("/add")
    public String addDriver(@Valid @ModelAttribute("model") AddDriverModel model, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "drivers/add";
        }

        AddDriverServiceModel serviceModel = mapper.map(model, AddDriverServiceModel.class);
        try {
            driverService.registerDriver(serviceModel);
            return "redirect:/drivers/all";
        } catch (Exception e) {
            return "redirect:/drivers/add";
        }
    }

    @GetMapping("/all")
    public ModelAndView getAllDrivers(ModelAndView modelAndView, HttpSession session) throws Exception {
        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        List<DriverViewModel> drivers = driverService.getAllDrivers()
                .stream()
                .map(driver -> mapper.map(driver, DriverViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("drivers", drivers);
        modelAndView.setViewName("/drivers/all");
        return modelAndView;
    }

    @GetMapping("/remove/{id}")
    public ModelAndView removeDriver(@PathVariable String id, HttpSession session) throws Exception {
        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdmin(user);

        driverService.removeDriver(id);
        return new ModelAndView("redirect:/drivers/all");
    }
}
