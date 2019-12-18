package truckmanagementproject.web.controllers.expenses;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.services.auth.AuthService;
import truckmanagementproject.services.services.cloudinary.CloudinaryService;
import truckmanagementproject.services.services.expenses.ExpenseService;
import truckmanagementproject.services.services.trips.TripService;
import truckmanagementproject.services.services.vehicles.VehicleService;
import truckmanagementproject.services.models.expenses.AddTripExpenseServiceModel;
import truckmanagementproject.services.models.expenses.AddVehicleExpenseServiceModel;
import truckmanagementproject.web.controllers.base.BaseController;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.expenses.AddTripExpenseModel;
import truckmanagementproject.web.models.expenses.AddVehicleExpenseModel;
import truckmanagementproject.web.models.expenses.TripExpenseViewModel;
import truckmanagementproject.web.models.expenses.VehicleExpenseViewModel;
import truckmanagementproject.web.models.trips.TripViewModel;
import truckmanagementproject.web.models.vehicles.VehicleViewModel;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/expenses")
public class ExpenseController extends BaseController {

    private final TripService tripService;
    private final VehicleService vehicleService;
    private final ExpenseService expenseService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper mapper;

    @Autowired
    public ExpenseController(TripService tripService, VehicleService vehicleService, ExpenseService expenseService, AuthService authService, CloudinaryService cloudinaryService, ModelMapper mapper) {
        super(authService);
        this.tripService = tripService;
        this.vehicleService = vehicleService;
        this.expenseService = expenseService;
        this.cloudinaryService = cloudinaryService;
        this.mapper = mapper;
    }

    @GetMapping("/trip/add")
    public ModelAndView getAddTripExpensePage(ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeDriver(user);

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

        if (!expenseService.isTripExpenseValid(addTripExpenseModel)) {
            LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
            String driverUsername = user.getUsername();

            List<TripViewModel> trips = tripService.getAllTripsByDriver(driverUsername)
                    .stream()
                    .map(tr -> mapper.map(tr, TripViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("trips", trips);
            modelAndView.addObject("isValid", false);
            modelAndView.setViewName("/expenses/trip/add");
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

            modelAndView.setViewName("redirect:/expenses/trip/all");
            return modelAndView;

        } catch (Exception e) {
            return new ModelAndView("redirect:/expenses/trip/add");
        }
    }

    @GetMapping("/trip/all")
    public ModelAndView getAllTripExpenses(ModelAndView modelAndView) {

        return modelAndView;
    }

    @GetMapping("/trip/remove/{id}")
    public ModelAndView removeTripExpense(@PathVariable String id, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        expenseService.removeTripExpense(id);
        return new ModelAndView("redirect:/expenses/trip/all");
    }

    @GetMapping("/trip/{reference}")
    public ModelAndView getTripExpensesByTrip(@PathVariable String reference, ModelAndView modelAndView) {
        List<TripExpenseViewModel> expenses = expenseService.getAllTripExpensesByTrip(reference)
                .stream()
                .map(exp -> mapper.map(exp, TripExpenseViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("expenses", expenses);
        modelAndView.setViewName("expenses/trip/all");
        return modelAndView;
    }

    @GetMapping("/vehicle/add")
    public ModelAndView getAddVehicleExpensePage(ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

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

        if (!expenseService.isVehicleExpenseValid(addVehicleExpenseModel)) {

            List<VehicleViewModel> vehicles = vehicleService.getAllVehicles()
                    .stream()
                    .map(vehicle -> mapper.map(vehicle, VehicleViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("vehicles", vehicles);
            modelAndView.addObject("isValid", false);
            modelAndView.setViewName("/expenses/vehicle/add");
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

            modelAndView.setViewName("redirect:/expenses/vehicle/all");
            return modelAndView;

        } catch (Exception e) {
            return new ModelAndView("redirect:/expenses/vehicle/add");
        }
    }

    @GetMapping("/vehicle/all")
    public ModelAndView getAllVehicleExpenses(ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        List<VehicleExpenseViewModel> expenses = expenseService.getAllVehicleExpenses()
                .stream()
                .map(expense -> mapper.map(expense, VehicleExpenseViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("expenses", expenses);
        modelAndView.setViewName("expenses/vehicle/all");

        return modelAndView;
    }

    @GetMapping("/vehicle/remove/{id}")
    public ModelAndView removeVehicleExpense(@PathVariable String id, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        expenseService.removeVehicleExpense(id);
        return new ModelAndView("redirect:/expenses/vehicle/all");
    }

    @GetMapping("/vehicle/{id}")
    public ModelAndView getVehicleExpensesByTrip(@PathVariable String id, ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        List<VehicleExpenseViewModel> expenses = expenseService.getAllVehicleExpensesByVehicle(id)
                .stream()
                .map(exp -> mapper.map(exp, VehicleExpenseViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("expenses", expenses);
        modelAndView.setViewName("expenses/vehicle/all");
        return modelAndView;
    }

}
