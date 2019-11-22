package truckmanagementproject.services;

import truckmanagementproject.services.models.AddManagerServiceModel;

public interface AddManagerValidationService {

    boolean isValid(AddManagerServiceModel model);

}
