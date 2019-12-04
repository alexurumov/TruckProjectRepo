package truckmanagementproject.services.services.validations;

import truckmanagementproject.services.models.drivers.AddDriverServiceModel;

public interface AddDriverValidationService {
    boolean isValid(AddDriverServiceModel model);
}
