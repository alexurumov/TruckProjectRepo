package truckmanagementproject.services.services.trips;

import truckmanagementproject.services.models.trips.AddTripServiceModel;
import truckmanagementproject.services.models.trips.FinishTripServiceModel;
import truckmanagementproject.services.models.trips.TripServiceModel;
import truckmanagementproject.web.models.expenses.AddTripExpenseModel;
import truckmanagementproject.web.models.expenses.AddVehicleExpenseModel;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public interface TripService {
    List<TripServiceModel> getAllTripsByDriver(String driverUsername);
    boolean isReferenceTaken(String reference);

    void addTrip(AddTripServiceModel tripServiceModel) throws Exception;

    List<TripServiceModel> getAllCurrent();

    TripServiceModel getTripByReference(String reference);

    void finishTrip(FinishTripServiceModel tripServiceModel, String reference) throws Exception;

    List<TripServiceModel> getAllFinished();

    void remove(String reference);

    List<TripServiceModel> getAllTripsByVehicle(String id);

    List<TripServiceModel> getAllTripsByDriverId(String id);

    boolean isTripExpenseValid(AddTripExpenseModel addTripExpenseModel);

    boolean isVehicleExpenseValid(AddVehicleExpenseModel addVehicleExpenseModel);
}
