package truckmanagementproject.web.controllers.documents;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.services.CloudinaryService;
import truckmanagementproject.services.DocumentService;
import truckmanagementproject.services.DriverService;
import truckmanagementproject.services.TripService;
import truckmanagementproject.services.models.documents.AddDriverDocServiceModel;
import truckmanagementproject.services.models.documents.AddTripDocServiceModel;
import truckmanagementproject.web.models.auth.LoginUserViewModel;
import truckmanagementproject.web.models.documents.AddDriverDocumentModel;
import truckmanagementproject.web.models.documents.AddTripDocumentModel;
import truckmanagementproject.web.models.drivers.DriverViewModel;
import truckmanagementproject.web.models.trips.TripViewModel;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private final TripService tripService;
    private final DocumentService documentService;
    private final DriverService driverService;
    private final ModelMapper mapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public DocumentController(TripService tripService, DocumentService documentService, DriverService driverService, ModelMapper mapper, CloudinaryService cloudinaryService) {
        this.tripService = tripService;
        this.documentService = documentService;
        this.driverService = driverService;
        this.mapper = mapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/trip/add")
    public ModelAndView getAddTripDocPage(ModelAndView modelAndView,
                                          HttpSession session) {

        modelAndView.setViewName("/documents/trip/add");
        LoginUserViewModel user = (LoginUserViewModel) session.getAttribute("user");
        String driverUsername = user.getUsername();

        List<TripViewModel> trips = tripService.getAllTripsByDriver(driverUsername)
                .stream()
                .map(tr -> mapper.map(tr, TripViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("trips", trips);
        return modelAndView;
    }

    @PostMapping("/trip/add")
    public String addTripDoc(AddTripDocumentModel addTripDocumentModel) {

        try {
            String picture = cloudinaryService.upload(addTripDocumentModel.getPicture());
            AddTripDocServiceModel tripDoc = mapper.map(addTripDocumentModel, AddTripDocServiceModel.class);
            tripDoc.setPicture(picture);
            documentService.addTripDocument(tripDoc);
            return "redirect:/documents/trip/all";

        } catch (IOException e) {
            return "redirect:/documents/trip/add";
        }
    }

    @GetMapping("/trip/all")
    public String getAllDriverDocs() {
//        documentService.getAllTripDocsByDriver(driverUsername from session)
        return "documents/trip/all";
    }

    @GetMapping("/driver/add")
    public ModelAndView getAddDriverDocPage(ModelAndView modelAndView) {
        modelAndView.setViewName("documents/driver/add");
        List<DriverViewModel> drivers = driverService.getAllDrivers()
                .stream()
                .map(driver -> mapper.map(driver, DriverViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("drivers", drivers);
        return modelAndView;
    }

    @PostMapping("driver/add")
    public ModelAndView addDriverDoc(@ModelAttribute AddDriverDocumentModel addDriverDocumentModel, ModelAndView modelAndView) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate expiryDate = LocalDate.parse(addDriverDocumentModel.getExpiryDate(), formatter);
        if (expiryDate.isBefore(LocalDate.now()) || expiryDate.isEqual(LocalDate.now()) || addDriverDocumentModel.getPicture().getOriginalFilename().isEmpty()) {
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
            AddDriverDocServiceModel docServiceModel = mapper.map(addDriverDocumentModel, AddDriverDocServiceModel.class);
            String picture = cloudinaryService.upload(addDriverDocumentModel.getPicture());
            docServiceModel.setPicture(picture);
            docServiceModel.setExpiryDate(expiryDate);

            documentService.addDriverDocument(docServiceModel);

            modelAndView.setViewName("documents/driver/all");
            modelAndView.addObject("isValid", true);
            return modelAndView;
        } catch (IOException e) {
            return new ModelAndView("redirect:/documents/driver/add");
        }
    }
}
