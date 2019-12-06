package truckmanagementproject.services.services.drivers;

import truckmanagementproject.services.models.drivers.AddDriverServiceModel;
import truckmanagementproject.services.models.drivers.DriverServiceModel;

import java.util.List;

public interface DriverService {
    void registerDriver (AddDriverServiceModel model) throws Exception;
    List<DriverServiceModel> getAllDrivers();

    void removeDriver(String id);

}
