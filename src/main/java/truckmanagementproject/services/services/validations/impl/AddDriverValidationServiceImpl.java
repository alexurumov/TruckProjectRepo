package truckmanagementproject.services.services.validations.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.repositories.users.DriverRepository;
import truckmanagementproject.services.services.validations.AddDriverValidationService;
import truckmanagementproject.services.models.drivers.AddDriverServiceModel;

@Service
public class AddDriverValidationServiceImpl implements AddDriverValidationService {

    private final DriverRepository driverRepository;

    @Autowired
    public AddDriverValidationServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    private boolean isNameFree(String name) {
        return !driverRepository.existsByName(name);
    }

    private boolean isUsernameFree(String username) {
        return !driverRepository.existsByUsername(username);
    }

    private boolean arePasswordsValid(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    @Override
    public boolean isValid(AddDriverServiceModel model) {
        return this.arePasswordsValid(model.getPassword(), model.getConfirmPassword()) &&
                this.isUsernameFree(model.getUsername()) &&
                this.isNameFree(model.getName());
    }
}
