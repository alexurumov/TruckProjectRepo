package truckmanagementproject.services.services.managers;

import truckmanagementproject.services.models.managers.AddManagerServiceModel;
import truckmanagementproject.services.models.managers.ManagerServiceModel;

import java.util.Arrays;
import java.util.List;

public interface ManagerService {
    void registerManager (AddManagerServiceModel model) throws Exception;

    List<ManagerServiceModel> getAllManagers();

    void removeManager(String id);
}
