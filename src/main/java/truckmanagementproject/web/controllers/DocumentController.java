package truckmanagementproject.web.controllers;

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
import truckmanagementproject.services.TripService;
import truckmanagementproject.services.models.AddTripDocServiceModel;
import truckmanagementproject.web.models.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private final TripService tripService;
    private final DocumentService documentService;
    private final ModelMapper mapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public DocumentController(TripService tripService, DocumentService documentService, ModelMapper mapper, CloudinaryService cloudinaryService) {
        this.tripService = tripService;
        this.documentService = documentService;
        this.mapper = mapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/trip/add")
    public ModelAndView getAddDriverDocPage(@ModelAttribute AddTripDocumentModel addTripDocumentModel,
                                            ModelAndView modelAndView,
                                            HttpSession session) {

        modelAndView.setViewName("/documents/trip-add");
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
    public String addDriverDoc(AddTripDocumentModel addTripDocumentModel) {

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
        return "documents/trip-all";
    }
}
