package truckmanagementproject.web.controllers.trips;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.models.milestones.AddMilestoneServiceModel;
import truckmanagementproject.services.models.trips.FinishTripServiceModel;
import truckmanagementproject.services.services.auth.AuthService;
import truckmanagementproject.services.services.drivers.DriverService;
import truckmanagementproject.services.services.milestones.MilestoneService;
import truckmanagementproject.services.services.trips.TripService;
import truckmanagementproject.services.services.vehicles.VehicleService;
import truckmanagementproject.services.models.trips.AddTripServiceModel;
import truckmanagementproject.services.models.trips.TripServiceModel;
import truckmanagementproject.web.controllers.base.BaseController;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.drivers.DriverViewModel;
import truckmanagementproject.web.models.milestones.AddMilestoneModel;
import truckmanagementproject.web.models.milestones.MilestoneViewModel;
import truckmanagementproject.web.models.trips.AddTripModel;
import truckmanagementproject.web.models.trips.FinishTripModel;
import truckmanagementproject.web.models.trips.TripViewModel;
import truckmanagementproject.web.models.vehicles.VehicleViewModel;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/trips")
public class TripController extends BaseController {

    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final TripService tripService;
    private final MilestoneService milestoneService;
    private final ModelMapper mapper;

    @Autowired
    public TripController(DriverService driverService, VehicleService vehicleService, TripService tripService, MilestoneService milestoneService, AuthService authService, ModelMapper mapper) {
        super(authService);
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.tripService = tripService;
        this.milestoneService = milestoneService;
        this.mapper = mapper;
    }

    @GetMapping("/add")
    public ModelAndView getTripAddForm(ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

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

    @PostMapping("/add")
    public ModelAndView tripAdd(@ModelAttribute AddTripModel addTripModel,
                                ModelAndView modelAndView) {

        if (!tripService.isAddTripModelValid(addTripModel)) {
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


        try {
            AddTripServiceModel tripServiceModel = mapper.map(addTripModel, AddTripServiceModel.class);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(addTripModel.getDate(), formatter);
            tripServiceModel.setDate(date);
            tripService.addTrip(tripServiceModel);
            return new ModelAndView("redirect:/trips/current");
        } catch (Exception e) {
            return new ModelAndView("redirect:/trips/add");
        }

    }

    @GetMapping("/current")
    public ModelAndView getAllCurrentTrips(ModelAndView modelAndView) {

        //Objects loaded with interceptor

        modelAndView.setViewName("trips/all-current");
        return modelAndView;
    }

    @GetMapping("/finished")
    public ModelAndView getAllFinishedTrips(ModelAndView modelAndView) {

        //Objects loaded with interceptor

        modelAndView.setViewName("trips/all-finished");
        return modelAndView;
    }

    @GetMapping("/finished/by-vehicle/{id}")
    public ModelAndView getAllFinishedTripsByVehicle(@PathVariable String id, ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        List<TripViewModel> trips = tripService.getAllTripsByVehicle(id)
                .stream()
                .filter(TripServiceModel::getIsFinished)
                .map(trip -> mapper.map(trip, TripViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("trips", trips);
        modelAndView.setViewName("trips/all-finished");
        return modelAndView;
    }

    @GetMapping("/finished/by-driver/{id}")
    public ModelAndView getAllFinishedTripsByDriver(@PathVariable String id, ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        List<TripViewModel> trips = tripService.getAllTripsByDriverId(id)
                .stream()
                .filter(TripServiceModel::getIsFinished)
                .map(trip -> mapper.map(trip, TripViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("trips", trips);
        modelAndView.setViewName("trips/all-finished");
        return modelAndView;
    }

    @GetMapping("/details/{reference}")
    public ModelAndView getTripDetails(@PathVariable String reference, ModelAndView modelAndView) throws Exception {

        TripServiceModel tripModel = tripService.getTripByReference(reference);
        TripViewModel trip = mapper.map(tripModel, TripViewModel.class);
        List<MilestoneViewModel> collections = tripModel.getMilestones()
                .stream()
                .filter(milestone -> milestone.getMilestoneType().equals("Collection"))
                .map(milestone -> mapper.map(milestone, MilestoneViewModel.class))
                .collect(Collectors.toList());

        List<MilestoneViewModel> deliveries = tripModel.getMilestones()
                .stream()
                .filter(milestone -> milestone.getMilestoneType().equals("Delivery"))
                .map(milestone -> mapper.map(milestone, MilestoneViewModel.class))
                .collect(Collectors.toList());

        trip.setCollections(collections);
        trip.setDeliveries(deliveries);
        modelAndView.addObject("trip", trip);
        modelAndView.setViewName("trips/trip-details");
        return modelAndView;
    }

    @GetMapping("/add-collection/{reference}")
    public ModelAndView getAddCollectionPage(@PathVariable String reference, ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        session.setAttribute("reference", reference);
        modelAndView.setViewName("/trips/add-collection");
        return modelAndView;
    }

    @PostMapping("/add-collection/{reference}")
    public ModelAndView addCollection(@ModelAttribute AddMilestoneModel addMilestoneModel,
                                      @PathVariable String reference,
                                      ModelAndView modelAndView,
                                      HttpSession session) {

        if (!milestoneService.isMilestoneValid(addMilestoneModel)) {
            session.setAttribute("reference", reference);
            modelAndView.addObject("isValid", false);
            modelAndView.setViewName("trips/add-collection");
            return modelAndView;
        }

        try {
            AddMilestoneServiceModel collection = mapper.map(addMilestoneModel, AddMilestoneServiceModel.class);
            collection.setMilestoneType("Collection");
            collection.setTripReference(reference);
            milestoneService.addMilestone(collection);
            modelAndView.setViewName("redirect:/trips/details/" + reference);
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/trips/details/" + reference);
        }
    }

    @GetMapping("/add-delivery/{reference}")
    public ModelAndView getAddDeliveryPage(@PathVariable String reference, ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        session.setAttribute("reference", reference);
        modelAndView.setViewName("/trips/add-delivery");
        return modelAndView;
    }

    @PostMapping("/add-delivery/{reference}")
    public ModelAndView addDelivery(@ModelAttribute AddMilestoneModel addMilestoneModel,
                                    @PathVariable String reference,
                                    ModelAndView modelAndView,
                                    HttpSession session) {

        if (!milestoneService.isMilestoneValid(addMilestoneModel)) {
            session.setAttribute("reference", reference);
            modelAndView.addObject("isValid", false);
            modelAndView.setViewName("trips/add-delivery");
            return modelAndView;
        }

        try {
            AddMilestoneServiceModel delivery = mapper.map(addMilestoneModel, AddMilestoneServiceModel.class);
            delivery.setMilestoneType("Delivery");
            delivery.setTripReference(reference);
            milestoneService.addMilestone(delivery);
            modelAndView.setViewName("redirect:/trips/details/" + reference);
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/trips/details/" + reference);
        }
    }

    @GetMapping("/finish-trip/{reference}")
    public ModelAndView getFinishTripPage(@PathVariable String reference, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeDriver(user);

        TripServiceModel trip = tripService.getTripByReference(reference);

        if (!tripService.areAllMilestonesFinished(trip)) {
            return new ModelAndView("redirect:/trips/details/" + reference);
        }

        session.setAttribute("reference", reference);
        return new ModelAndView("trips/finish-trip");
    }

    @PostMapping("/finish-trip/{reference}")
    public ModelAndView finishTrip(@ModelAttribute FinishTripModel finishTripModel,
                                   @PathVariable String reference,
                                   ModelAndView modelAndView,
                                   HttpSession session) {

        if (!tripService.isFinishTripValid(finishTripModel)) {
            session.setAttribute("reference", reference);
            modelAndView.addObject("isValid", false);
            modelAndView.setViewName("trips/finish-trip");
            return modelAndView;
        }

        try {
            FinishTripServiceModel tripServiceModel = mapper.map(finishTripModel, FinishTripServiceModel.class);
            tripService.finishTrip(tripServiceModel, reference);
            return new ModelAndView("redirect:/trips/finished");
        } catch (Exception e) {
            return new ModelAndView("redirect:/trips/finish-trip/" + reference);
        }
    }

    @GetMapping("/finish-milestone/{id}")
    private String finishMilestone(@PathVariable String id, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeDriver(user);

        milestoneService.updateMilestone(id);
        String reference = milestoneService.getById(id).getTripReference();
        return "redirect:/trips/details/" + reference;
    }

    @GetMapping("/remove/{reference}")
    public ModelAndView removeTrip(@PathVariable String reference, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        this.tripService.remove(reference);
        return new ModelAndView("redirect:/trips/finished");
    }

}
