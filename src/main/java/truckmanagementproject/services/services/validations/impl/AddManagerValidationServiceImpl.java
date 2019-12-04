package truckmanagementproject.services.services.validations.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truckmanagementproject.data.repositories.users.ManagerRepository;
import truckmanagementproject.services.services.validations.AddManagerValidationService;
import truckmanagementproject.services.models.managers.AddManagerServiceModel;

@Service
public class AddManagerValidationServiceImpl implements AddManagerValidationService {

    private final ManagerRepository managerRepository;

    @Autowired
    public AddManagerValidationServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public boolean isValid(AddManagerServiceModel model) {
        return this.arePasswordsValid(model.getPassword(), model.getConfirmPassword()) &&
                this.isUsernameFree(model.getUsername()) &&
                this.isNameFree(model.getName());
    }

    private boolean isNameFree(String name) {
        return !managerRepository.existsByName(name);
    }

    private boolean isUsernameFree(String username) {
        return !managerRepository.existsByUsername(username);
    }

    private boolean arePasswordsValid(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
