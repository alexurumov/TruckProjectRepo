package truckmanagementproject.services;

import truckmanagementproject.services.models.vehicles.AddVehicleServiceModel;

public interface VehicleService {
    void registerVehicle (AddVehicleServiceModel model) throws Exception;
}
