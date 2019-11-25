package truckmanagementproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import truckmanagementproject.services.VehicleService;
import truckmanagementproject.services.models.AddVehicleServiceModel;
import truckmanagementproject.web.models.AddVehicleModel;

import javax.validation.Valid;

@Controller
public class VehicleController {

    @ModelAttribute
    public AddVehicleModel model() {
        return new AddVehicleModel();
    };

    private final ModelMapper mapper;
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(ModelMapper mapper, VehicleService vehicleService) {
        this.mapper = mapper;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles/add")
    public String getAddVehicleForm(@ModelAttribute("model") AddVehicleModel model) {
        return "vehicles/add-vehicle";
    }

    @PostMapping("/vehicles/add")
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

    @GetMapping("/vehicles/all")
    public String getAllVehicles() {
        return "vehicles/all-vehicles";
    }
}
