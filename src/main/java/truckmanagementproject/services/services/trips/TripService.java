package truckmanagementproject.services.services.trips;

import truckmanagementproject.services.models.trips.AddTripServiceModel;
import truckmanagementproject.services.models.trips.TripServiceModel;

import java.util.List;

public interface TripService {
    List<TripServiceModel> getAllTripsByDriver(String driverUsername);
    boolean isReferenceTaken(String reference);

    void addTrip(AddTripServiceModel tripServiceModel);

    List<TripServiceModel> getAllCurrent();

    TripServiceModel getTripByReference(String reference);
}