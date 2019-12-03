package truckmanagementproject.services;

import truckmanagementproject.services.models.managers.AddManagerServiceModel;

public interface AddManagerValidationService {

    boolean isValid(AddManagerServiceModel model);

}
