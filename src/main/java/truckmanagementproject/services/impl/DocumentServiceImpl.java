package truckmanagementproject.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.documents.DriverDocument;
import truckmanagementproject.data.models.documents.TripDocument;
import truckmanagementproject.data.models.documents.VehicleDocument;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.models.vehicles.Vehicle;
import truckmanagementproject.data.repositories.documents.DriverDocumentRepository;
import truckmanagementproject.data.repositories.documents.TripDocumentRepository;
import truckmanagementproject.data.repositories.documents.VehicleDocumentRepository;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.data.repositories.vehicles.VehicleRepository;
import truckmanagementproject.services.DocumentService;
import truckmanagementproject.services.models.documents.AddDriverDocServiceModel;
import truckmanagementproject.services.models.documents.AddTripDocServiceModel;
import truckmanagementproject.services.models.documents.AddVehicleDocServiceModel;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final TripDocumentRepository tripDocumentRepository;
    private final DriverDocumentRepository driverDocumentRepository;
    private final VehicleDocumentRepository vehicleDocumentRepository;
    private final DriverRepository driverRepository;
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapper mapper;

    @Autowired
    public DocumentServiceImpl(TripDocumentRepository tripDocumentRepository, DriverDocumentRepository driverDocumentRepository, VehicleDocumentRepository vehicleDocumentRepository, DriverRepository driverRepository, TripRepository tripRepository, VehicleRepository vehicleRepository, ModelMapper mapper) {
        this.tripDocumentRepository = tripDocumentRepository;
        this.driverDocumentRepository = driverDocumentRepository;
        this.vehicleDocumentRepository = vehicleDocumentRepository;
        this.driverRepository = driverRepository;
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
        this.mapper = mapper;
    }

    @Override
    public void addTripDocument(AddTripDocServiceModel model) {
        TripDocument tripDocument = mapper.map(model, TripDocument.class);
        Trip trip = tripRepository.getByReference(model.getTripRef());
        tripDocument.setTrip(trip);
        tripDocumentRepository.saveAndFlush(tripDocument);
    }

    @Override
    public void addDriverDocument(AddDriverDocServiceModel docServiceModel) {
        DriverDocument document = mapper.map(docServiceModel, DriverDocument.class);
        Driver driver = driverRepository.getByName(docServiceModel.getDriverName());
        document.setDriver(driver);
        driverDocumentRepository.saveAndFlush(document);
    }

    @Override
    public void addVehicleDocument(AddVehicleDocServiceModel docServiceModel) {
        VehicleDocument document = mapper.map(docServiceModel, VehicleDocument.class);
        Vehicle vehicle = vehicleRepository.getByRegNumber(docServiceModel.getRegNumber());
        document.setVehicle(vehicle);
        vehicleDocumentRepository.saveAndFlush(document);
    }
}
