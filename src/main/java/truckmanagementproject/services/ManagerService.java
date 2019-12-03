package truckmanagementproject.services;

import truckmanagementproject.services.models.managers.AddManagerServiceModel;

public interface ManagerService {
    void registerManager (AddManagerServiceModel model) throws Exception;
}
