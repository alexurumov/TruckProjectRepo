package truckmanagementproject.web.controllers.documents;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.services.auth.AuthService;
import truckmanagementproject.services.services.cloudinary.CloudinaryService;
import truckmanagementproject.services.services.documents.DocumentService;
import truckmanagementproject.services.services.drivers.DriverService;
import truckmanagementproject.services.models.documents.AddCompanyDocServiceModel;
import truckmanagementproject.services.models.documents.AddDriverDocServiceModel;
import truckmanagementproject.services.models.documents.AddTripDocServiceModel;
import truckmanagementproject.services.models.documents.AddVehicleDocServiceModel;
import truckmanagementproject.services.services.trips.TripService;
import truckmanagementproject.services.services.vehicles.VehicleService;
import truckmanagementproject.web.controllers.base.BaseController;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.documents.*;
import truckmanagementproject.web.models.drivers.DriverViewModel;
import truckmanagementproject.web.models.trips.TripViewModel;
import truckmanagementproject.web.models.vehicles.VehicleViewModel;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/documents")
public class DocumentController extends BaseController {

    private final TripService tripService;
    private final DocumentService documentService;
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final ModelMapper mapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public DocumentController(TripService tripService, DocumentService documentService, DriverService driverService, VehicleService vehicleService, AuthService authService, ModelMapper mapper, CloudinaryService cloudinaryService) {
        super(authService);
        this.tripService = tripService;
        this.documentService = documentService;
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.mapper = mapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/trip/add")
    public ModelAndView getAddTripDocPage(ModelAndView modelAndView,
                                          HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeDriver(user);

        String driverUsername = user.getUsername();

        List<TripViewModel> trips = tripService.getAllTripsByDriver(driverUsername)
                .stream()
                .map(tr -> mapper.map(tr, TripViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("trips", trips);
        modelAndView.setViewName("/documents/trip/add");
        return modelAndView;
    }

    @PostMapping("/trip/add")
    public ModelAndView addTripDoc(AddTripDocumentModel addTripDocumentModel, ModelAndView modelAndView, HttpSession session) {
        if (!documentService.isTripDocValid(addTripDocumentModel)) {
            LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
            String driverUsername = user.getUsername();

            List<TripViewModel> trips = tripService.getAllTripsByDriver(driverUsername)
                    .stream()
                    .map(tr -> mapper.map(tr, TripViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.setViewName("/documents/trip/add");
            modelAndView.addObject("trips", trips);
            modelAndView.addObject("isValid", false);
            return modelAndView;
        }

        try {
            String picture = cloudinaryService.upload(addTripDocumentModel.getPicture());
            AddTripDocServiceModel tripDoc = mapper.map(addTripDocumentModel, AddTripDocServiceModel.class);
            tripDoc.setPicture(picture);
            documentService.addTripDocument(tripDoc);
            modelAndView.setViewName("redirect:/documents/trip/all");
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/documents/trip/add");
        }
    }

    @GetMapping("/trip/all")
    public ModelAndView getAllTripDocs(ModelAndView modelAndView) {
        // List loaded by Interceptor
        return modelAndView;
    }

    @GetMapping("/trip/remove/{id}")
    public ModelAndView removeTripDoc(@PathVariable String id, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        documentService.removeTripDocument(id);
        return new ModelAndView("redirect:/documents/trip/all");
    }

    @GetMapping("/trip/{reference}")
    public ModelAndView getTripDocumentsByTrip(@PathVariable String reference, ModelAndView modelAndView) {
        List<TripDocumentViewModel> documents = documentService.getAllTripDocsByTrip(reference)
                .stream()
                .map(doc -> mapper.map(doc, TripDocumentViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("documents", documents);
        modelAndView.setViewName("documents/trip/all");
        return modelAndView;
    }

    @GetMapping("/vehicle/add")
    public ModelAndView getAddVehicleDocPage(ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        modelAndView.setViewName("documents/vehicle/add");
        List<VehicleViewModel> vehicles = vehicleService.getAllVehicles()
                .stream()
                .map(vehicle -> mapper.map(vehicle, VehicleViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("vehicles", vehicles);
        return modelAndView;
    }

    @PostMapping("vehicle/add")
    public ModelAndView addVehicleDoc(@ModelAttribute AddVehicleDocumentModel addVehicleDocumentModel, ModelAndView modelAndView) {

        if (!documentService.isVehicleDocValid(addVehicleDocumentModel)) {
            List<VehicleViewModel> vehicles = vehicleService.getAllVehicles()
                    .stream()
                    .map(driver -> mapper.map(driver, VehicleViewModel.class))
                    .collect(Collectors.toList());

            modelAndView.setViewName("documents/vehicle/add");
            modelAndView.addObject("vehicles", vehicles);
            modelAndView.addObject("isValid", false);
            return modelAndView;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate expiryDate = LocalDate.parse(addVehicleDocumentModel.getExpiryDate(), formatter);
            AddVehicleDocServiceModel docServiceModel = mapper.map(addVehicleDocumentModel, AddVehicleDocServiceModel.class);
            String picture = cloudinaryService.upload(addVehicleDocumentModel.getPicture());
            docServiceModel.setPicture(picture);
            docServiceModel.setExpiryDate(expiryDate);

            documentService.addVehicleDocument(docServiceModel);

            modelAndView.setViewName("redirect:/documents/vehicle/all");
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/documents/vehicle/add");
        }
    }

    @GetMapping("/vehicle/all")
    public ModelAndView getAllVehicleDocs(ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        List<VehicleDocumentViewModel> documents = documentService.getAllVehicleDocs()
                .stream()
                .map(doc -> mapper.map(doc, VehicleDocumentViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("documents", documents);
        modelAndView.setViewName("documents/vehicle/all");
        return modelAndView;
    }

    @GetMapping("/vehicle/{id}")
    public ModelAndView getVehicleDocumentsByTrip(@PathVariable String id, ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        List<VehicleDocumentViewModel> documents = documentService.getAllVehicleDocumentsByVehicle(id)
                .stream()
                .map(doc -> mapper.map(doc, VehicleDocumentViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("documents", documents);
        modelAndView.setViewName("documents/vehicle/all");
        return modelAndView;
    }

    @GetMapping("/vehicle/remove/{id}")
    public ModelAndView removeVehicleDoc(@PathVariable String id, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        documentService.removeVehicleDocument(id);
        return new ModelAndView("redirect:/documents/vehicle/all");
    }

    @GetMapping("/driver/add")
    public ModelAndView getAddDriverDocPage(ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        List<DriverViewModel> drivers = driverService.getAllDrivers()
                .stream()
                .map(driver -> mapper.map(driver, DriverViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("drivers", drivers);
        modelAndView.setViewName("documents/driver/add");
        return modelAndView;
    }

    @PostMapping("driver/add")
    public ModelAndView addDriverDoc(@ModelAttribute AddDriverDocumentModel addDriverDocumentModel, ModelAndView modelAndView) {

        if (!documentService.isDriverDocValid(addDriverDocumentModel)) {
            List<DriverViewModel> drivers = driverService.getAllDrivers()
                    .stream()
                    .map(driver -> mapper.map(driver, DriverViewModel.class))
                    .collect(Collectors.toList());

            modelAndView.setViewName("documents/driver/add");
            modelAndView.addObject("drivers", drivers);
            modelAndView.addObject("isValid", false);
            return modelAndView;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate expiryDate = LocalDate.parse(addDriverDocumentModel.getExpiryDate(), formatter);
            AddDriverDocServiceModel docServiceModel = mapper.map(addDriverDocumentModel, AddDriverDocServiceModel.class);
            String picture = cloudinaryService.upload(addDriverDocumentModel.getPicture());
            docServiceModel.setPicture(picture);
            docServiceModel.setExpiryDate(expiryDate);

            documentService.addDriverDocument(docServiceModel);

            modelAndView.setViewName("redirect:/documents/driver/all");
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/documents/driver/add");
        }
    }

    @GetMapping("/driver/all")
    public ModelAndView getAllDriverDocs(ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        List<DriverDocumentViewModel> documents = documentService.getAllDriverDocs()
                .stream()
                .map(doc -> mapper.map(doc, DriverDocumentViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("documents", documents);
        modelAndView.setViewName("documents/driver/all");
        return modelAndView;
    }

    @GetMapping("/driver/{id}")
    public ModelAndView getDriverDocumentsByDriver(@PathVariable String id, ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        List<DriverDocumentViewModel> documents = documentService.getAllDriverDocsByDriver(id)
                .stream()
                .map(doc -> mapper.map(doc, DriverDocumentViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("documents", documents);
        modelAndView.setViewName("documents/driver/all");
        return modelAndView;
    }

    @GetMapping("/driver/remove/{id}")
    public ModelAndView removeDriverDoc(@PathVariable String id, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        documentService.removeDriverDocument(id);
        return new ModelAndView("redirect:/documents/driver/all");
    }

    @GetMapping("/company/add")
    public ModelAndView getAddCompanyDocPage(ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        modelAndView.setViewName("documents/company/add");
        return modelAndView;
    }

    @PostMapping("company/add")
    public ModelAndView addCompanyDoc(@ModelAttribute AddCompanyDocumentModel addCompanyDocumentModel, ModelAndView modelAndView) {

        if (!documentService.isCompanyDocValid(addCompanyDocumentModel)) {
            modelAndView.setViewName("documents/company/add");
            modelAndView.addObject("isValid", false);
            return modelAndView;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate expiryDate = LocalDate.parse(addCompanyDocumentModel.getExpiryDate(), formatter);
            AddCompanyDocServiceModel docServiceModel = mapper.map(addCompanyDocumentModel, AddCompanyDocServiceModel.class);
            String picture = cloudinaryService.upload(addCompanyDocumentModel.getPicture());
            docServiceModel.setPicture(picture);
            docServiceModel.setExpiryDate(expiryDate);

            documentService.addCompanyDocument(docServiceModel);

            modelAndView.setViewName("redirect:/documents/company/all");
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/documents/company/add");
        }
    }

    @GetMapping("/company/all")
    public ModelAndView getAllCompanyDocs(ModelAndView modelAndView, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        List<CompanyDocumentViewModel> documents = documentService.getAllCompanyDocs()
                .stream()
                .map(doc -> mapper.map(doc, CompanyDocumentViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("documents", documents);
        modelAndView.setViewName("documents/company/all");
        return modelAndView;
    }


    @GetMapping("/company/remove/{id}")
    public ModelAndView removeCompanyDoc(@PathVariable String id, HttpSession session) throws Exception {

        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        authorizeAdminAndManager(user);

        documentService.removeCompanyDocument(id);
        return new ModelAndView("redirect:/documents/company/all");
    }

}
