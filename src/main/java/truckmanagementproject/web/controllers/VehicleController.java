package truckmanagementproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import truckmanagementproject.services.VehicleService;
import truckmanagementproject.services.models.AddVehicleServiceModel;
import truckmanagementproject.web.models.AddVehicleModel;

@Controller
public class VehicleController {

    private final ModelMapper mapper;
    private final VehicleService vehicleService;

    public VehicleController(ModelMapper mapper, VehicleService vehicleService) {
        this.mapper = mapper;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles/add")
    public String getAddVehicleForm() {
        return "vehicles/add-vehicle";
    }

    @PostMapping("/vehicles/add")
    public String addVehicle(@ModelAttribute AddVehicleModel model) {
        AddVehicleServiceModel vehicle = mapper.map(model, AddVehicleServiceModel.class);
        try {
            vehicleService.registerVehicle(vehicle);
            return "redirect:/vehicles/all";
        } catch (Exception e) {
            return "redirect:/vehicles/add";
        }
    }

    @GetMapping("/vehicles/all")
    public String getAllVehicles() {
        return "vehicles/all-vehicles";
    }
}
