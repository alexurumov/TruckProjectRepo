package truckmanagementproject.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import truckmanagementproject.web.models.AddManagerModel;

@Controller
public class ManagerController {

    @GetMapping("/managers/add")
    public String getAddManagerForm() {
        return "managers/add-manager";
    }

    @PostMapping("/managers/add")
    public String addManager(@ModelAttribute AddManagerModel model) {
        //service -> add manager
        System.out.println();
        return "redirect:/managers/all";
    }

    @GetMapping("/managers/all")
    public String getAllDrivers() {
        return "managers/all-managers";
    }
}
