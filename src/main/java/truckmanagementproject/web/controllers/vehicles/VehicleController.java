package truckmanagementproject.web.controllers.vehicles;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.services.auth.AuthService;
import truckmanagementproject.services.services.vehicles.VehicleService;
import truckmanagementproject.services.models.vehicles.AddVehicleServiceModel;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.vehicles.AddVehicleModel;
import truckmanagementproject.web.models.vehicles.VehicleViewModel;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    @ModelAttribute
    public AddVehicleModel model() {
        return new AddVehicleModel();
    };

    private final ModelMapper mapper;
    private final VehicleService vehicleService;
    private final AuthService authService;

    @Autowired
    public VehicleController(ModelMapper mapper, VehicleService vehicleService, AuthService authService) {
        this.mapper = mapper;
        this.vehicleService = vehicleService;
        this.authService = authService;
    }

    @GetMapping("/add")
    public String getAddVehicleForm(@ModelAttribute("model") AddVehicleModel model, HttpSession session) throws Exception {
        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        if (!authService.isUserAdmin(user)) {
            throw new Exception("Unauthorized user");
        }
        return "vehicles/add-vehicle";
    }

    @PostMapping("/add")
    public String addVehicle(@Valid @ModelAttribute("model") AddVehicleModel model, BindingResult bindingResult) {
        AddVehicleServiceModel vehicle = mapper.map(model, AddVehicleServiceModel.class);

        if (bindingResult.hasErrors()) {
            return "vehicles/add-vehicle";
        }

        try {
            vehicleService.registerVehicle(vehicle);
            return "redirect:/vehicles/all";
        } catch (Exception e) {
            return "redirect:/vehicles/add";
        }
    }

    @GetMapping("/all")
    public ModelAndView getAllVehicles(ModelAndView modelAndView, HttpSession session) throws Exception {
        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        if (!authService.isUserAdmin(user) && !authService.isUserManager(user)) {
            throw new Exception("Unauthorized user");
        }
        List<VehicleViewModel> vehicles = vehicleService.getAllVehicles()
                .stream()
                .map(vehicle -> mapper.map(vehicle, VehicleViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("vehicles", vehicles);
        modelAndView.setViewName("/vehicles/all");
        return modelAndView;
    }

    @GetMapping("/remove/{id}")
    public ModelAndView removeDriverDoc(@PathVariable String id, HttpSession session) throws Exception {
        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        if (!authService.isUserAdmin(user)) {
            throw new Exception("Unauthorized user");
        }
        vehicleService.removeVehicle(id);
        return new ModelAndView("redirect:/vehicles/all");
    }
}
