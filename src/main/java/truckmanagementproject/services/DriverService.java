package truckmanagementproject.services;

import truckmanagementproject.services.models.AddDriverServiceModel;

public interface DriverService {
    void registerDriver (AddDriverServiceModel model) throws Exception;
}
