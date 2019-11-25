package truckmanagementproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import truckmanagementproject.services.ManagerService;
import truckmanagementproject.services.models.AddManagerServiceModel;
import truckmanagementproject.web.models.AddManagerModel;

import javax.validation.Valid;

@Controller
public class ManagerController {

    @ModelAttribute
    public AddManagerModel model() {
        return new AddManagerModel();
    }

    private final ModelMapper mapper;
    private final ManagerService managerService;

    @Autowired
    public ManagerController(ModelMapper mapper, ManagerService managerService) {
        this.mapper = mapper;
        this.managerService = managerService;
    }

    @GetMapping("/managers/add")
    public String getAddManagerForm(@ModelAttribute("model") AddManagerModel model) {
        return "managers/add-manager";
    }

    @PostMapping("/managers/add")
    public String addManager(@Valid @ModelAttribute("model") AddManagerModel model, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "managers/add-manager";
        }

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
