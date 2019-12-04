package truckmanagementproject.web.controllers.trips;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.DriverService;
import truckmanagementproject.services.VehicleService;
import truckmanagementproject.web.models.drivers.DriverViewModel;
import truckmanagementproject.web.models.trips.AddTripModel;
import truckmanagementproject.web.models.vehicles.VehicleViewModel;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TripController {

    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final ModelMapper mapper;

    @Autowired
    public TripController(DriverService driverService, VehicleService vehicleService, ModelMapper mapper) {
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.mapper = mapper;
    }

    @GetMapping("/trips/add")
    public ModelAndView getTripAddForm(ModelAndView modelAndView) {
        List<DriverViewModel> drivers = driverService.getAllDrivers()
                .stream()
                .map(driver -> mapper.map(driver, DriverViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("drivers", drivers);

        List<VehicleViewModel> vehicles = vehicleService.getAllVehicles()
                .stream()
                .map(vehicle -> mapper.map(vehicle, VehicleViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("vehicles", vehicles);
        modelAndView.setViewName("trips/add");
        return modelAndView;
    }

    @PostMapping("/trips/add")
    public String tripAdd(@ModelAttribute AddTripModel model) {
        //service -> add trip
        System.out.println();
        return "redirect:/trips/current";
    }

    @GetMapping("/trips/current")
    public String getAllCurrentTrips() {
        return "trips/all-current-trips";
    }

    @GetMapping("/trips/finished")
    public String getAllFinishedTrips() {
        return "trips/all-finished-trips";
    }
}
