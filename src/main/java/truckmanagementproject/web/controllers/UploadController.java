package truckmanagementproject.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import truckmanagementproject.data.models.documents.TripDocument;
import truckmanagementproject.data.models.documents.TripDocumentType;
import truckmanagementproject.data.repositories.documents.TripDocumentRepository;
import truckmanagementproject.data.repositories.trips.TripRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class UploadController {
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
    private final TripDocumentRepository tripDocumentRepository;
    private final TripRepository tripRepository;

    @Autowired
    public UploadController(TripDocumentRepository tripDocumentRepository, TripRepository tripRepository) {
        this.tripDocumentRepository = tripDocumentRepository;
        this.tripRepository = tripRepository;
    }

    @GetMapping("/upload")
    public String getForm(Model model) {
        return "form";
    }

    @Transactional
    @PostMapping("/upload")
    public String getFormConfirm(Model model, @RequestParam("file") MultipartFile file) {

        String fileName = file.getOriginalFilename().toString();
        int dot = fileName.lastIndexOf('.');
        String extension = fileName.substring(dot);
        String newFileName = "LGQY21204" + "_" + "CMR" + "_" + (tripDocumentRepository.findAll().size() + 1) + extension;

        Path fileNameAndPath = Paths.get(uploadDirectory, newFileName);
        try {
            Files.write(fileNameAndPath, file.getBytes());
            TripDocument tripDocument = new TripDocument();
            tripDocument.setType(TripDocumentType.CMR);
            tripDocument.setPicture(newFileName);
            tripDocument.setTrip(tripRepository.getByReference("LGQY21204"));
            tripDocumentRepository.saveAndFlush(tripDocument);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<TripDocument> docs = tripDocumentRepository.findAll();
        model.addAttribute("docs", docs);
        model.addAttribute("msg", "Successfully uploaded file " + newFileName);
        return "after-upload";
    }

    @GetMapping("/after-upload")
    public String getAfterUpload(Model model) {
        return "form";
    }
}
