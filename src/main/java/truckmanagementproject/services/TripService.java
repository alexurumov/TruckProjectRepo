package truckmanagementproject.services;

import truckmanagementproject.services.models.TripServiceModel;

import java.util.List;

public interface TripService {
    List<TripServiceModel> getAllTripsByDriver(String driverUsername);
}
