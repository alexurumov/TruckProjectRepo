package truckmanagementproject.services.services.validations;

import org.springframework.stereotype.Service;
import truckmanagementproject.services.models.managers.AddManagerServiceModel;

public interface AddManagerValidationService {

    boolean isValid(AddManagerServiceModel model);

}
