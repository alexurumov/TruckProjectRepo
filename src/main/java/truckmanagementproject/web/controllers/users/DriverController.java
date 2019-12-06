package truckmanagementproject.web.controllers.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.services.drivers.DriverService;
import truckmanagementproject.services.models.drivers.AddDriverServiceModel;
import truckmanagementproject.web.models.drivers.AddDriverModel;
import truckmanagementproject.web.models.drivers.DriverViewModel;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/drivers")
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

    @GetMapping("/add")
    public String getAddDriverForm(@ModelAttribute("model") AddDriverModel model) {
        return "drivers/add-driver";
    }

    @PostMapping("/add")
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

    @GetMapping("/all")
    public ModelAndView getAllDrivers(ModelAndView modelAndView) {
        List<DriverViewModel> drivers = driverService.getAllDrivers()
                .stream()
                .map(driver -> mapper.map(driver, DriverViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("drivers", drivers);
        modelAndView.setViewName("/drivers/all");
        return modelAndView;
    }

    @GetMapping("/remove/{id}")
    public ModelAndView removeDriver(@PathVariable String id) {
        driverService.removeDriver(id);
        return new ModelAndView("redirect:/drivers/all");
    }


}
