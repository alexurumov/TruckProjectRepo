package truckmanagementproject.services;

import truckmanagementproject.services.models.AddVehicleServiceModel;

public interface VehicleService {
    void registerVehicle (AddVehicleServiceModel model) throws Exception;
}
