package truckmanagementproject.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.documents.DriverDocument;
import truckmanagementproject.data.models.documents.TripDocument;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.models.users.Driver;
import truckmanagementproject.data.repositories.documents.DriverDocumentRepository;
import truckmanagementproject.data.repositories.documents.TripDocumentRepository;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.services.DocumentService;
import truckmanagementproject.services.models.documents.AddDriverDocServiceModel;
import truckmanagementproject.services.models.documents.AddTripDocServiceModel;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final TripDocumentRepository tripDocumentRepository;
    private final DriverDocumentRepository driverDocumentRepository;
    private final DriverRepository driverRepository;
    private final TripRepository tripRepository;
    private final ModelMapper mapper;

    @Autowired
    public DocumentServiceImpl(TripDocumentRepository tripDocumentRepository, DriverDocumentRepository driverDocumentRepository, DriverRepository driverRepository, TripRepository tripRepository, ModelMapper mapper) {
        this.tripDocumentRepository = tripDocumentRepository;
        this.driverDocumentRepository = driverDocumentRepository;
        this.driverRepository = driverRepository;
        this.tripRepository = tripRepository;
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
}
