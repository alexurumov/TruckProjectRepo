package truckmanagementproject.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import truckmanagementproject.web.models.AddVehicleModel;

@Controller
public class VehicleController {

    @GetMapping("/vehicles/add")
    public String getAddVehicleForm() {
        return "vehicles/add-vehicle";
    }

    @PostMapping("/vehicles/add")
    public String addVehicle(@ModelAttribute AddVehicleModel model) {
        //service -> add vehicle
        System.out.println();
        return "redirect:/vehicles/all";
    }

    @GetMapping("/vehicles/all")
    public String getAllVehicles() {
        return "vehicles/all-vehicles";
    }
}
