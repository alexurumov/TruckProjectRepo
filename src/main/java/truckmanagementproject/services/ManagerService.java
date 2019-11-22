package truckmanagementproject.services;

import truckmanagementproject.services.models.AddManagerServiceModel;

public interface ManagerService {
    void registerManager (AddManagerServiceModel model) throws Exception;
}
