package truckmanagementproject.services.services.documents;

import truckmanagementproject.services.models.documents.*;

import java.util.Arrays;
import java.util.List;

public interface DocumentService {
    void addTripDocument(AddTripDocServiceModel model);

    void addDriverDocument(AddDriverDocServiceModel docServiceModel);

    void addVehicleDocument(AddVehicleDocServiceModel docServiceModel);

    void addCompanyDocument(AddCompanyDocServiceModel docServiceModel);

    List<TripDocumentServiceModel> getAllTripDocs();

    List<TripDocumentServiceModel> getAllTripDocsByDriver(String username);

    List<TripDocumentServiceModel> getAllTripDocsByTrip(String reference);

    void removeTripDocument(String id);

    List<VehicleDocumentServiceModel> getAllVehicleDocs();

    List<VehicleDocumentServiceModel> getAllVehicleDocumentsByVehicle(String id);

    void removeVehicleDocument(String id);

    List<DriverDocumentServiceModel> getAllDriverDocs();

    void removeDriverDocument(String id);

    List<CompanyDocumentServiceModel> getAllCompanyDocs();

    void removeCompanyDocument(String id);

    List<DriverDocumentServiceModel> getAllDriverDocsByDriver(String id);
}
