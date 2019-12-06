package truckmanagementproject.services.services.trips;

import truckmanagementproject.services.models.trips.AddTripServiceModel;
import truckmanagementproject.services.models.trips.FinishTripServiceModel;
import truckmanagementproject.services.models.trips.TripServiceModel;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public interface TripService {
    List<TripServiceModel> getAllTripsByDriver(String driverUsername);
    boolean isReferenceTaken(String reference);

    void addTrip(AddTripServiceModel tripServiceModel);

    List<TripServiceModel> getAllCurrent();

    TripServiceModel getTripByReference(String reference);

    void finishTrip(FinishTripServiceModel tripServiceModel, String reference);

    List<TripServiceModel> getAllFinished();

    void remove(String reference);

    List<TripServiceModel> getAllTripsByVehicle(String id);

    List<TripServiceModel> getAllTripsByDriverId(String id);
}
