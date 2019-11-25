package truckmanagementproject.services;

import truckmanagementproject.services.models.AddDriverServiceModel;

public interface AddDriverValidationService {
    boolean isValid(AddDriverServiceModel model);
}
