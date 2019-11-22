package truckmanagementproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import truckmanagementproject.services.ManagerService;
import truckmanagementproject.services.models.AddManagerServiceModel;
import truckmanagementproject.web.models.AddManagerModel;

@Controller
public class ManagerController {

    private final ModelMapper mapper;
    private final ManagerService managerService;

    @Autowired
    public ManagerController(ModelMapper mapper, ManagerService managerService) {
        this.mapper = mapper;
        this.managerService = managerService;
    }

    @GetMapping("/managers/add")
    public String getAddManagerForm() {
        return "managers/add-manager";
    }

    @PostMapping("/managers/add")
    public String addManager(@ModelAttribute AddManagerModel model) {

        //TODO implement validation and view returning message with Thymeleaf

        AddManagerServiceModel serviceModel = mapper.map(model, AddManagerServiceModel.class);
        try {
            managerService.registerManager(serviceModel);
            return "redirect:/managers/all";
        } catch (Exception e) {
            //DO Something
            return "redirect:/managers/add";
        }
    }

    @GetMapping("/managers/all")
    public String getAllDrivers() {
        return "managers/all-managers";
    }
}
