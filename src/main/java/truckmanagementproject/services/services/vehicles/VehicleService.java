package truckmanagementproject.services.services.vehicles;

import truckmanagementproject.services.models.vehicles.AddVehicleServiceModel;
import truckmanagementproject.services.models.vehicles.VehicleServiceModel;

import java.util.List;

public interface VehicleService {
    void registerVehicle (AddVehicleServiceModel model) throws Exception;

    List<VehicleServiceModel> getAllVehicles();

    void removeVehicle(String id);
}
