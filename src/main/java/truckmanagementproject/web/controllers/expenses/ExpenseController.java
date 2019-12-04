package truckmanagementproject.web.controllers.expenses;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.services.cloudinary.CloudinaryService;
import truckmanagementproject.services.services.expenses.ExpenseService;
import truckmanagementproject.services.services.trips.TripService;
import truckmanagementproject.services.services.vehicles.VehicleService;
import truckmanagementproject.services.models.expenses.AddTripExpenseServiceModel;
import truckmanagementproject.services.models.expenses.AddVehicleExpenseServiceModel;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.expenses.AddTripExpenseModel;
import truckmanagementproject.web.models.expenses.AddVehicleExpenseModel;
import truckmanagementproject.web.models.trips.TripViewModel;
import truckmanagementproject.web.models.vehicles.VehicleViewModel;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final TripService tripService;
    private final VehicleService vehicleService;
    private final ExpenseService expenseService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper mapper;

    @Autowired
    public ExpenseController(TripService tripService, VehicleService vehicleService, ExpenseService expenseService, CloudinaryService cloudinaryService, ModelMapper mapper) {
        this.tripService = tripService;
        this.vehicleService = vehicleService;
        this.expenseService = expenseService;
        this.cloudinaryService = cloudinaryService;
        this.mapper = mapper;
    }

    @GetMapping("/trip/add")
    public ModelAndView getAddTripExpensePage(ModelAndView modelAndView, HttpSession session) {
        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        String driverUsername = user.getUsername();

        List<TripViewModel> trips = tripService.getAllTripsByDriver(driverUsername)
                .stream()
                .map(tr -> mapper.map(tr, TripViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("trips", trips);
        modelAndView.setViewName("/expenses/trip/add");
        return modelAndView;
    }

    @PostMapping("/trip/add")
    public ModelAndView addTripExpense(@ModelAttribute AddTripExpenseModel addTripExpenseModel,
                                       ModelAndView modelAndView,
                                       HttpSession session) {

        if (!isTripExpenseValid(addTripExpenseModel)) {
            LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
            String driverUsername = user.getUsername();

            List<TripViewModel> trips = tripService.getAllTripsByDriver(driverUsername)
                    .stream()
                    .map(tr -> mapper.map(tr, TripViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("trips", trips);
            modelAndView.setViewName("/expenses/trip/add");
            modelAndView.addObject("isValid", false);
            return modelAndView;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(addTripExpenseModel.getDate(), formatter);

            String picture = cloudinaryService.upload(addTripExpenseModel.getPicture());
            AddTripExpenseServiceModel tripExpense = mapper.map(addTripExpenseModel, AddTripExpenseServiceModel.class);
            tripExpense.setPicture(picture);
            tripExpense.setDate(date);
            expenseService.addTripExpense(tripExpense);

            modelAndView.setViewName("expenses/trip/all");
            return modelAndView;

        } catch (Exception e) {
            return new ModelAndView("redirect:/expenses/trip/add");
        }
    }

    @GetMapping("/vehicle/add")
    public ModelAndView getAddVehicleExpensePage(ModelAndView modelAndView) {

        List<VehicleViewModel> vehicles = vehicleService.getAllVehicles()
                .stream()
                .map(vehicle -> mapper.map(vehicle, VehicleViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("vehicles", vehicles);
        modelAndView.setViewName("/expenses/vehicle/add");
        return modelAndView;
    }

    @PostMapping("/vehicle/add")
    public ModelAndView addVehicleExpense(@ModelAttribute AddVehicleExpenseModel addVehicleExpenseModel,
                                       ModelAndView modelAndView) {

        if (!isVehicleExpenseValid(addVehicleExpenseModel)) {

            List<VehicleViewModel> vehicles = vehicleService.getAllVehicles()
                    .stream()
                    .map(vehicle -> mapper.map(vehicle, VehicleViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("vehicles", vehicles);
            modelAndView.setViewName("/expenses/vehicle/add");
            modelAndView.addObject("isValid", false);
            return modelAndView;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(addVehicleExpenseModel.getDate(), formatter);

            String picture = cloudinaryService.upload(addVehicleExpenseModel.getPicture());
            AddVehicleExpenseServiceModel vehicleExpense = mapper.map(addVehicleExpenseModel, AddVehicleExpenseServiceModel.class);
            vehicleExpense.setDate(date);
            vehicleExpense.setPicture(picture);
            expenseService.addVehicleExpense(vehicleExpense);

            modelAndView.setViewName("expenses/vehicle/all");
            return modelAndView;

        } catch (Exception e) {
            return new ModelAndView("redirect:/expenses/vehicle/add");
        }
    }

    private boolean isVehicleExpenseValid(AddVehicleExpenseModel addVehicleExpenseModel) {
        if (addVehicleExpenseModel.getDate().trim().isEmpty()) {
            return false;
        }
        return !addVehicleExpenseModel.getPicture().getOriginalFilename().isEmpty() &&
                !addVehicleExpenseModel.getVehicleRegNumber().equals("0") &&
                addVehicleExpenseModel.getCost().compareTo(BigDecimal.ZERO) > 0;
    }


    private boolean isTripExpenseValid(AddTripExpenseModel addTripExpenseModel) {
        if (addTripExpenseModel.getDate().trim().isEmpty()) {
            return false;
        }
        return !addTripExpenseModel.getPicture().getOriginalFilename().isEmpty() &&
                !addTripExpenseModel.getTripRef().equals("0") &&
                addTripExpenseModel.getCost().compareTo(BigDecimal.ZERO) > 0;
    }
}
