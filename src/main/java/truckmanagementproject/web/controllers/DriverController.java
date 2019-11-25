package truckmanagementproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import truckmanagementproject.services.DriverService;
import truckmanagementproject.services.models.AddDriverServiceModel;
import truckmanagementproject.services.models.AddManagerServiceModel;
import truckmanagementproject.web.models.AddDriverModel;

import javax.validation.Valid;

@Controller
public class DriverController {

    @ModelAttribute
    public AddDriverModel model() {
        return new AddDriverModel();
    }

    private final ModelMapper mapper;
    private final DriverService driverService;

    @Autowired
    public DriverController(ModelMapper mapper, DriverService driverService) {
        this.mapper = mapper;
        this.driverService = driverService;
    }

    @GetMapping("/drivers/add")
    public String getAddDriverForm(@ModelAttribute("model") AddDriverModel model) {
        return "drivers/add-driver";
    }

    @PostMapping("/drivers/add")
    public String addDriver(@Valid @ModelAttribute("model") AddDriverModel model, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "drivers/add-driver";
        }

        AddDriverServiceModel serviceModel = mapper.map(model, AddDriverServiceModel.class);
        try {
            driverService.registerDriver(serviceModel);
            return "redirect:/drivers/all";
        } catch (Exception e) {
            return "redirect:/drivers/add";
        }
    }

    @GetMapping("/drivers/all")
    public String getAllDrivers() {
        return "drivers/all-drivers";
    }
}
