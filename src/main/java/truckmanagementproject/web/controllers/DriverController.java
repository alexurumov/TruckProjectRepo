package truckmanagementproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import truckmanagementproject.services.DriverService;
import truckmanagementproject.services.models.AddDriverServiceModel;
import truckmanagementproject.services.models.AddManagerServiceModel;
import truckmanagementproject.web.models.AddDriverModel;

@Controller
public class DriverController {

    private final ModelMapper mapper;
    private final DriverService driverService;

    @Autowired
    public DriverController(ModelMapper mapper, DriverService driverService) {
        this.mapper = mapper;
        this.driverService = driverService;
    }

    @GetMapping("/drivers/add")
    public String getAddDriverForm() {
        return "drivers/add-driver";
    }

    @PostMapping("/drivers/add")
    public String addDriver(@ModelAttribute AddDriverModel model) {

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
