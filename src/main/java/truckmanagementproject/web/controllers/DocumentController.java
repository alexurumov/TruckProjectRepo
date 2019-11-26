package truckmanagementproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.services.TripService;
import truckmanagementproject.web.models.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private final TripService tripService;
    private final ModelMapper mapper;

    @Autowired
    public DocumentController(TripService tripService, ModelMapper mapper) {
        this.tripService = tripService;
        this.mapper = mapper;
    }

    @ModelAttribute
    public AddDriverDocumentModel driverDocumentModel() {
        return new AddDriverDocumentModel();
    };

    @ModelAttribute
    public AddTripDocumentModel tripDocumentModel() {
        return new AddTripDocumentModel();
    };

    @ModelAttribute
    public AddVehicleDocumentModel vehicleDocumentModel() {
        return new AddVehicleDocumentModel();
    };

    @ModelAttribute
    public AddCompanyDocumentModel companyDocumentModel() {
        return new AddCompanyDocumentModel();
    };



    @GetMapping("/trip/add")
    public ModelAndView getAddDriverDocPage(@ModelAttribute("tripDocumentModel") AddTripDocumentModel addTripDocumentModel,
                                            ModelAndView modelAndView,
                                            HttpSession session) {
        modelAndView.setViewName("trip-add");
//        String driverUsername = (String) session.getAttribute("user.username");

        String driverUsername = "IKaramihalev";

        List<TripViewModel> trips = tripService.getAllTripsByDriver(driverUsername)
                .stream()
                .map(tr -> mapper.map(tr, TripViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("trips", trips);
        return modelAndView;
    }

    @PostMapping("/trip/add")
    public String addDriverDoc(@Valid @ModelAttribute("tripDocumentModel") AddTripDocumentModel addTripDocumentModel,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "documents/trip-add";
        }

        try {
            //documentService -> addTripDocument
            return "redirect:/documents/trip-all";
        } catch (Exception e) {
            return "redirect:/documents/trip-add";
        }
    }

    @GetMapping("/trip/all")
    public String getAllDriverDocs() {
//        documentService.getAllTripDocsByDriver(driverUsername from session)
        return "documents/driver-all";
    }
}
