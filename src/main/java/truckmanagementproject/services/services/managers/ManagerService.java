package truckmanagementproject.services.services.managers;

import truckmanagementproject.services.models.managers.AddManagerServiceModel;

public interface ManagerService {
    void registerManager (AddManagerServiceModel model) throws Exception;
}
