package truckmanagementproject.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import truckmanagementproject.web.models.AddDriverModel;

@Controller
public class DriverController {

    @GetMapping("/drivers/add")
    public String getAddDriverForm() {
        return "add-driver";
    }

    @PostMapping("/drivers/add")
    public String addDriver(@ModelAttribute AddDriverModel model) {
        //service -> add driver
        System.out.println();
        return "redirect:/all-drivers";
    }
}
