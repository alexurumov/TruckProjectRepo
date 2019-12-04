package truckmanagementproject.web.controllers.trips;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.DriverService;
import truckmanagementproject.services.TripService;
import truckmanagementproject.services.VehicleService;
import truckmanagementproject.services.models.trips.AddTripServiceModel;
import truckmanagementproject.web.models.drivers.DriverViewModel;
import truckmanagementproject.web.models.trips.AddTripModel;
import truckmanagementproject.web.models.vehicles.VehicleViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class TripController {

    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final TripService tripService;
    private final ModelMapper mapper;

    @Autowired
    public TripController(DriverService driverService, VehicleService vehicleService, TripService tripService, ModelMapper mapper) {
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.tripService = tripService;
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
    public ModelAndView tripAdd(@ModelAttribute AddTripModel addTripModel,
                                ModelAndView modelAndView,
                                BindingResult bindingResult) {

        if (!isAddTripModelValid(addTripModel)) {
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
            modelAndView.addObject("isValid", false);
            return modelAndView;
        }

        AddTripServiceModel tripServiceModel = mapper.map(addTripModel, AddTripServiceModel.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(addTripModel.getDate(), formatter);
        tripServiceModel.setDate(date);

        tripService.addTrip(tripServiceModel);

        return new ModelAndView("trips/all-current");
    }

    private boolean isAddTripModelValid(AddTripModel addTripModel) {
        Pattern reference = Pattern.compile("[A-Z0-9]+");
        Matcher refMatcher = reference.matcher(addTripModel.getReference());
        if (!refMatcher.find()) {
            return false;
        }

        return !tripService.isReferenceTaken(addTripModel.getReference()) &&
                !addTripModel.getDriverName().equals("0") &&
                !addTripModel.getDate().trim().isEmpty() &&
                !addTripModel.getVehicleRegNumber().equals("0");
    }

    @GetMapping("/trips/current")
    public String getAllCurrentTrips() {
        return "all-current";
    }

    @GetMapping("/trips/finished")
    public String getAllFinishedTrips() {
        return "trips/all-finished-trips";
    }
}
