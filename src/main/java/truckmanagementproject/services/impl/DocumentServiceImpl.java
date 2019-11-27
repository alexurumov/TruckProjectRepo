package truckmanagementproject.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.models.documents.TripDocument;
import truckmanagementproject.data.models.trips.Trip;
import truckmanagementproject.data.repositories.documents.TripDocumentRepository;
import truckmanagementproject.data.repositories.trips.TripRepository;
import truckmanagementproject.services.DocumentService;
import truckmanagementproject.services.models.AddTripDocServiceModel;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final TripDocumentRepository tripDocumentRepository;
    private final TripRepository tripRepository;
    private final ModelMapper mapper;

    @Autowired
    public DocumentServiceImpl(TripDocumentRepository tripDocumentRepository, TripRepository tripRepository, ModelMapper mapper) {
        this.tripDocumentRepository = tripDocumentRepository;
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
}
