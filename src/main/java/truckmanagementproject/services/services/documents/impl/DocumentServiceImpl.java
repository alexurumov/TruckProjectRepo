package truckmanagementproject.services.services.documents.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.documents.CompanyDocument;
import truckmanagementproject.data.models.documents.DriverDocument;
import truckmanagementproject.data.models.documents.TripDocument;
import truckmanagementproject.data.models.documents.VehicleDocument;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.documents.CompanyDocumentRepository;
import truckmanagementproject.data.repositories.documents.DriverDocumentRepository;
import truckmanagementproject.data.repositories.documents.TripDocumentRepository;
import truckmanagementproject.data.repositories.documents.VehicleDocumentRepository;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.models.documents.*;
import truckmanagementproject.services.services.documents.DocumentService;
import truckmanagementproject.util.ValidationUtil;
import truckmanagementproject.web.models.documents.AddCompanyDocumentModel;
import truckmanagementproject.web.models.documents.AddDriverDocumentModel;
import truckmanagementproject.web.models.documents.AddTripDocumentModel;
import truckmanagementproject.web.models.documents.AddVehicleDocumentModel;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final TripDocumentRepository tripDocumentRepository;
    private final DriverDocumentRepository driverDocumentRepository;
    private final VehicleDocumentRepository vehicleDocumentRepository;
    private final CompanyDocumentRepository companyDocumentRepository;
    private final DriverRepository driverRepository;
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public DocumentServiceImpl(TripDocumentRepository tripDocumentRepository, DriverDocumentRepository driverDocumentRepository, VehicleDocumentRepository vehicleDocumentRepository, CompanyDocumentRepository companyDocumentRepository, DriverRepository driverRepository, TripRepository tripRepository, VehicleRepository vehicleRepository, ModelMapper mapper, ValidationUtil validationUtil) {
        this.tripDocumentRepository = tripDocumentRepository;
        this.driverDocumentRepository = driverDocumentRepository;
        this.vehicleDocumentRepository = vehicleDocumentRepository;
        this.companyDocumentRepository = companyDocumentRepository;
        this.driverRepository = driverRepository;
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addTripDocument(AddTripDocServiceModel model) throws Exception {
        TripDocument tripDocument = mapper.map(model, TripDocument.class);
        Trip trip = tripRepository.getByReference(model.getTripRef());
        tripDocument.setTrip(trip);
        if (validationUtil.isValid(tripDocument)) {
            tripDocumentRepository.saveAndFlush(tripDocument);
        } else  {
            throw new Exception("Invalid Trip Document");
        }
    }

    @Override
    public void addDriverDocument(AddDriverDocServiceModel docServiceModel) throws Exception {
        DriverDocument document = mapper.map(docServiceModel, DriverDocument.class);
        Driver driver = driverRepository.getByName(docServiceModel.getDriverName());
        document.setDriver(driver);

        if (validationUtil.isValid(document)) {
            driverDocumentRepository.saveAndFlush(document);
        } else {
            throw new Exception("Invalid Driver Document");
        }
    }

    @Override
    public void addVehicleDocument(AddVehicleDocServiceModel docServiceModel) throws Exception {
        VehicleDocument document = mapper.map(docServiceModel, VehicleDocument.class);
        Vehicle vehicle = vehicleRepository.getByRegNumber(docServiceModel.getRegNumber());
        document.setVehicle(vehicle);

        if (validationUtil.isValid(document)) {
            vehicleDocumentRepository.saveAndFlush(document);
        } else {
            throw new Exception("Invalid Vehicle Document");
        }
    }

    @Override
    public void addCompanyDocument(AddCompanyDocServiceModel docServiceModel) throws Exception {
        CompanyDocument document = mapper.map(docServiceModel, CompanyDocument.class);

        if (validationUtil.isValid(document)) {
            companyDocumentRepository.saveAndFlush(document);
        } else {
            throw new Exception("Invalid Company Document");
        }
    }

    @Override
    public List<TripDocumentServiceModel> getAllTripDocs() {
        return tripDocumentRepository.findAll()
                .stream()
                .map(doc -> mapper.map(doc, TripDocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDocumentServiceModel> getAllTripDocsByDriver(String username) {
        return tripDocumentRepository.getAllByTripDriverUsername(username)
                .stream()
                .map(doc -> mapper.map(doc, TripDocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDocumentServiceModel> getAllTripDocsByTrip(String reference) {
        return tripDocumentRepository.getAllByTripReference(reference)
                .stream()
                .map(doc -> mapper.map(doc, TripDocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeTripDocument(String id) {
        tripDocumentRepository.deleteById(id);
    }

    @Override
    public List<VehicleDocumentServiceModel> getAllVehicleDocs() {
        return vehicleDocumentRepository.findAll()
                .stream()
                .map(doc -> mapper.map(doc, VehicleDocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeVehicleDocument(String id) {
        vehicleDocumentRepository.deleteById(id);
    }

    @Override
    public List<DriverDocumentServiceModel> getAllDriverDocs() {
        return driverDocumentRepository.findAll()
                .stream()
                .map(doc -> mapper.map(doc, DriverDocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeDriverDocument(String id) {
        driverDocumentRepository.deleteById(id);
    }

    @Override
    public List<CompanyDocumentServiceModel> getAllCompanyDocs() {
        return companyDocumentRepository.findAll()
                .stream()
                .map(doc -> mapper.map(doc, CompanyDocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeCompanyDocument(String id) {
        companyDocumentRepository.deleteById(id);
    }

    ////
    @Override
    public List<VehicleDocumentServiceModel> getAllVehicleDocumentsByVehicle(String id) {
        return vehicleDocumentRepository.getAllByVehicleId(id)
                .stream()
                .map(doc -> mapper.map(doc, VehicleDocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DriverDocumentServiceModel> getAllDriverDocsByDriver(String id) {
        return driverDocumentRepository.getAllByDriverId(id)
                .stream()
                .map(doc -> mapper.map(doc, DriverDocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isTripDocValid(AddTripDocumentModel addTripDocumentModel) {
        return !addTripDocumentModel.getPicture().getOriginalFilename().isEmpty() &&
                !addTripDocumentModel.getTripRef().equals("0");
    }

    @Override
    public boolean isVehicleDocValid(AddVehicleDocumentModel addVehicleDocumentModel) {
        if (addVehicleDocumentModel.getExpiryDate().trim().isEmpty()) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate expiryDate = LocalDate.parse(addVehicleDocumentModel.getExpiryDate(), formatter);

        return expiryDate.isAfter(LocalDate.now()) &&
                !addVehicleDocumentModel.getPicture().getOriginalFilename().isEmpty() &&
                !addVehicleDocumentModel.getRegNumber().equals("0");
    }

    @Override
    public boolean isDriverDocValid(AddDriverDocumentModel addDriverDocumentModel) {
        if (addDriverDocumentModel.getExpiryDate().trim().isEmpty()) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate expiryDate = LocalDate.parse(addDriverDocumentModel.getExpiryDate(), formatter);
        return expiryDate.isAfter(LocalDate.now()) &&
                !addDriverDocumentModel.getPicture().getOriginalFilename().isEmpty() &&
                !addDriverDocumentModel.getDriverName().equals("0");
    }

    @Override
    public boolean isCompanyDocValid(AddCompanyDocumentModel addCompanyDocumentModel) {
        if (addCompanyDocumentModel.getExpiryDate().trim().isEmpty()) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate expiryDate = LocalDate.parse(addCompanyDocumentModel.getExpiryDate(), formatter);

        return expiryDate.isAfter(LocalDate.now()) &&
                !addCompanyDocumentModel.getPicture().getOriginalFilename().isEmpty();
    }
}
